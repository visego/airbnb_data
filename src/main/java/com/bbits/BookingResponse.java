package com.bbits;

import java.io.IOException;

import org.jsoup.Jsoup;

import com.bbits.dto.BookingsInformation;
import com.google.gson.Gson;

/**
 * Class to work with API of AirBnB. Uses Gson to manipulate JSON data
 * 
 * @author visego
 */
public class BookingResponse {
	
	public final String HOST = "https://www.airbnb.es";
	
	public final String URL_DATA = HOST + "/api/v2/calendar_months?key=d306zoyjsyarp7ifhu67rjxn52tv0t20&currency=EUR&locale=es&listing_id=%s&month=%s&year=%s&count=%s&_format=with_conditions";
	
	/**
	 *  Retrieve data days of an acommodation. Includes information as price, available.
	 * 
	 * @param id_location 
	 * @param month_start
	 * @param year
	 * @param number_months
	 * @return data with the details of the available dates of this accomodation in the days requested.
	 * @throws IOException
	 */
	public BookingsInformation getBookings (String id_location, String month_start, String year, String number_months) throws IOException{
		
		final String query_url = String.format(URL_DATA, id_location, month_start, year, number_months);
		String json = Jsoup.connect(query_url).ignoreContentType(true).execute().body();
		
		// from JSON to object 
		Gson gson = new Gson();
		BookingsInformation output = gson.fromJson(json,BookingsInformation.class);
		
		return output;
	}

}
