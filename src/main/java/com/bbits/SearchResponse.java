package com.bbits;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class SearchResponse {
	
	public final String PATTERN_URL = "data-url=";
	
	public final String MEDIA_PHOTO = "media-photo media-cover";
	
	public final String PATTERN_NAME = "data-name";
	
	public final String HOST = "https://www.airbnb.es";
	
	public final String URL_PAGE = HOST + "/s/Fuengirola--Espa%C3%B1a?page=";
	
	public final String URL_DATA = HOST + "/api/v2/calendar_months?key=d306zoyjsyarp7ifhu67rjxn52tv0t20&currency=EUR&locale=es&listing_id=%s&month=%s&year=%s&count=%s&_format=with_conditions";
	
	/**
	 * 
	 * @param url
	 * @param page
	 * @return A list of all URL locations for the accomodations of a city
	 * @throws IOException
	 */
	
	//TODO Avoid introduce number of page of the city, scan the number of total pages in the first request
	//TODO Change the url parameter by a name of the city
	public List<String> getLocationsByCity(String url, int page) throws IOException{
		ArrayList<String> output = new ArrayList<String>();
		
		for (int i=1; i<page ; i++){
        	output.addAll(getLocationsByPage(URL_PAGE +i));
        }
		return output;
		
	}
	
	private List<String> getLocationsByPage(String url) throws IOException{
		Document doc = Jsoup.connect(url).get();
		System.out.println(url);
		
		Elements masthead = doc.select("div.MEDIA_PHOTO");
		System.out.println(masthead);
		
		Elements links = doc.select("a[href]");
		ArrayList<String> url_location_page = new ArrayList<String>();
		
		for (Element element : links) {
			if (MEDIA_PHOTO.equals(element.attributes().get("class"))){
				System.out.println(HOST.concat(element.attributes().get("href")));
				url_location_page.add(HOST.concat(element.attributes().get("href")));
			}			
		}
		return url_location_page;
	}

	/**
	 * 
	 * @param url
	 * @return latitude, longitude of the accomodation
	 * @throws IOException
	 */
	public List<String> getDataLocation(String url) throws IOException{
		
		Document doc = Jsoup.connect(url).get();
		Elements metaOgTitleLatitude = doc.select("meta[property=airbedandbreakfast:location:latitude]");
		Elements metaOgTitleLongitude = doc.select("meta[property=airbedandbreakfast:location:longitude]");
		
		ArrayList<String> position = new ArrayList<String>();
		position.add(metaOgTitleLatitude.get(0).attributes().get("content"));
		position.add(metaOgTitleLongitude.get(0).attributes().get("content"));
		
		return position;
	}
	
	public DayInformation getBookings (String id_location, String month_start, String year, String number_months) throws IOException{
		
		final String query_url = String.format(URL_DATA, id_location, month_start, year, number_months);
		String json = Jsoup.connect(query_url).ignoreContentType(true).execute().body();
		
		// from JSON to object 
		Gson gson = new Gson();
		DayInformation output = gson.fromJson(json,DayInformation.class);
		
		return output;
	}
	
}
