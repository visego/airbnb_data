package com.bbits;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.bbits.dto.BookingsInformation;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import dao.Locations;

/**
 * 
 * Class to work with the HTTP Response.
 * Uses Jsoup library to work with HTTP and Gson to manipulate JSON data
 * 
 * @author visego
 *
 */
public class SearchResponse {
	
	public final String PATTERN_URL = "data-url=";
	
	public final String MEDIA_PHOTO = "media-photo media-cover";
	
	public final String PATTERN_NAME = "data-name";
	
	public final String HOST = "https://www.airbnb.es";
	
	public final String URL_PAGE = HOST + "/s/Fuengirola--Espa%C3%B1a?page=";
	
	public final String URL_PAGE_2 = HOST + "/s/Fuengirola--España?room_types%5B%5D=Entire+home%2Fapt&ss_id=l1orjwf2&page=2&s_tag=jqIktzdI";
	
	public final String SPECIAL_CHARACTER = "?";
	
	public final String TYPE_OF_LOCATION_ENTIRE = "Casa/apto. entero";
	
	public final String TYPE_OF_LOCATION_SHARED_ROOM = "Habitación compartida";
	
	public final String TYPE_OF_LOCATION_PRIVATE_ROOM = "Habitación privada";
	
	/**
	 * URL of the locations. It can be duplicated. By that, this method should be called by getLocationsByCityProcessed to delete it 
	 * 
	 * @param url
	 * @param page number of total pages of this location
	 * @return A list of all URL locations for the accomodations of a city
	 * @throws IOException
	 */
	
	//TODO Avoid introduce number of page of the city, scan the number of total pages in the first request
	//TODO Change the url parameter by a name of the city
	public List<String> getLocationsByCity(String url, int pages) throws IOException{
		ArrayList<String> output = new ArrayList<String>();
		System.out.println("Processing request....");
		
		for (int i=1; i<pages ; i++){
        	output.addAll(getLocationsByPage(URL_PAGE +i));
        }
		return output;
		
	}
	
	/**

	 * @param url
	 * @param page number of total pages of this location
	 * @return A HashMap of all URL locations for the accomodations of a city. The key is the id of the accomodation annd
	 * 			the value is the url of it.
	 * @throws IOException
	 */
	
	//TODO Avoid introduce number of page of the city, scan the number of total pages in the first request
	//TODO Change the url parameter by a name of the city
	public Map<Integer, String> getLocationsByCityProcessed(List<String> locations) throws IOException{
		Map<Integer, String> output = new HashMap<Integer,String>();
		
		int end_index = 0;
		int j=0;
		
		for (String url : locations) {
			
			String clave = url.substring(28);
			for (int i=0; i<clave.length(); i++){
				if (SPECIAL_CHARACTER.equals(clave.substring(i, i+1))){
					end_index = i;
					break;
				}
			}
			output.put(Integer.parseInt(clave.substring(0, end_index)), url);
			j++;
		}
		
		return output;
		
	}
	
	private List<String> getLocationsByPage(String url) throws IOException{
		Document doc = Jsoup.connect(url).timeout(0).get();
		System.out.println(url);
		
		Elements masthead = doc.select("div.MEDIA_PHOTO");
		System.out.println(masthead);
		
		Elements links = doc.select("a[href]");
		ArrayList<String> url_location_page = new ArrayList<String>();
		
		for (Element element : links) {
			if (MEDIA_PHOTO.equals(element.attributes().get("class"))){
				url_location_page.add(HOST.concat(element.attributes().get("href")));
			}			
		}
		return url_location_page;
	}

	/**
	 * Retrieve latitude and longitude of the accomodation
	 * 
	 * @param url Url of this location. Example: https://www.airbnb.es/rooms/1067652?s=5nej1Cvz
	 * @return latitude, longitude of the accomodation
	 * @throws IOException
	 */
	public List<String> getDataLocation(String url) throws IOException{
		
		Document doc = Jsoup.connect(url).timeout(0).get();
		Elements metaOgTitleLatitude = doc.select("meta[property=airbedandbreakfast:location:latitude]");
		Elements metaOgTitleLongitude = doc.select("meta[property=airbedandbreakfast:location:longitude]");
		Elements metaDescription = doc.select("meta[property=og:title]");
		Elements metaLocation    = doc.select("meta[property=airbedandbreakfast:locality]");
		Elements metaTypeOfLocation = doc.select("meta[name=description]");
		Elements metaURL = doc.select("meta[property=og:url]");
		
		ArrayList<String> positionAndDescription = new ArrayList<String>();
		positionAndDescription.add(metaOgTitleLatitude.get(0).attributes().get("content"));
		positionAndDescription.add(metaOgTitleLongitude.get(0).attributes().get("content"));
		positionAndDescription.add(metaDescription.get(0).attributes().get("content"));
		positionAndDescription.add(metaLocation.get(0).attributes().get("content"));
		
		final String typeOfRoom = metaTypeOfLocation.get(0).attributes().get("content");
		
		if(typeOfRoom.contains(TYPE_OF_LOCATION_ENTIRE)){
			positionAndDescription.add("Entire");
		}else{
			if(typeOfRoom.contains(TYPE_OF_LOCATION_PRIVATE_ROOM)){
				positionAndDescription.add("Private");
			}else{
				positionAndDescription.add("Shared");
			}
		}
		
		positionAndDescription.add(metaURL.get(0).attributes().get("content"));
		
		return positionAndDescription;
	}
	
	/**
	 *  Save data of location, city and description of map in the argument
	 * @param map Key is the id of the acommodation and String is the URL of it
	 * @throws Exception
	 */
	public void saveDataToBBDD(final Map<Integer, String> map) throws Exception{
		final Iterator<Entry<Integer, String>> it = map.entrySet().iterator();

		Entry<Integer, String> entry= null;
	    String url=null;

		Integer id =null;

		Locations locations = new Locations();
		
		int count_new = 0;
		int checked = 0;
		
		while (it.hasNext()) {
			entry= it.next();
			
			id = entry.getKey(); 
			url = entry.getValue(); 
			
			boolean isSavedLocation = locations.getDataByID(Integer.toString(id));
			if (!isSavedLocation){
				// Call to method data location to retrieve longitude and latitude of the acommodation
				List<String> location = getDataLocation(url);
				
				if (location.get(3).equals("Fuengirola")){
					// Save data in BBDD
					final String description_processed = location.get(2).replace("'","''");
					locations.saveData(Integer.toString(id), location.get(3), description_processed, location.get(0), location.get(1), location.get(4));
					System.out.println(String.format("The information for accomodation id %s is NOT saved", id));
					System.out.println("SAVING...");
					count_new++;
				}else{
					System.out.println(String.format("The information for accomodation id %s is NOT saved but it is %s", id,location.get(3)));
					System.out.println("NO SAVING!!!!!");
				}
					
			}
			else{
				System.out.println(String.format("The information for accomodation id %s is saved", id));
				checked++;
			}
			
		}

		System.out.println("New flats: " + count_new);
		System.out.println("Flats checked: " + checked);
	}
	
	
}
