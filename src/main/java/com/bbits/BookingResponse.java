package com.bbits;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

import com.bbits.dto.BookingsInformation;
import com.bbits.dto.Calendar_months;
import com.bbits.dto.Days;
import com.google.gson.Gson;

import dao.Bookings;

/**
 * Class to work with API of AirBnB. Uses Gson to manipulate JSON data
 * 
 * @author visego
 */
public class BookingResponse {
	
	public final String HOST = "https://www.airbnb.es";
	
	public final String URL_DATA = HOST + "/api/v2/calendar_months?key=d306zoyjsyarp7ifhu67rjxn52tv0t20&currency=EUR&locale=es&listing_id=%s&month=%s&year=%s&count=%s&_format=with_conditions";
	
	/**
	 *  Retrieve data days of an acommodation. Includes information as price, available. HttpStatusException is shown when a location has been deleted and 
	 *  we are trying to get booking dates.
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
		String json = null;
		try{
			json = Jsoup.connect(query_url).timeout(0).ignoreContentType(true).execute().body();
			// from JSON to object 
			Gson gson = new Gson();
			BookingsInformation output = gson.fromJson(json,BookingsInformation.class);
			
			return output;
		}catch(HttpStatusException e) {
			return null;
		}	
		
	}
	
	public void saveBookingInformation(final String id_location, final BookingsInformation bookings, final List<String> months) throws Exception{
		
		Bookings locations = new Bookings();
		
		Calendar_months calendar  = bookings.getCalendarMonth(0);
    	Days[] days = calendar.getDays();
    
    	
    	for (Days day : days) {
    		List<String> isSavedDay = locations.getBookByIDAndDate(id_location,day.getDate());
    						
    		if (isSavedDay.get(0).equals("false")){
    			locations.saveDataDays(id_location, day.getDate(), day.getPrice().getLocal_price(), day.getAvailable(),true);
    			System.out.println(String.format("Saving day... id_location %s and day %s",id_location,day.getDate()));
    		}else{
    			if (!isSavedDay.get(1).equals(day.getPrice().getLocal_price()) || !isSavedDay.get(2).equals(String.valueOf(day.getAvailable())) ){
    				locations.saveDataDays(id_location, day.getDate(), day.getPrice().getLocal_price(), day.getAvailable(),false);
    				System.out.println(String.format("Saving day... id_location %s and day %s with changes",id_location,day.getDate()));
    			}
    			else{
    				System.out.println(String.format("Day previously saved for %s and day %s without updates ",id_location,day.getDate()));
    			}
    				
    		}
    		
		}
		
	}
	
public void saveBookingInformationComplete(final String id_location, final BookingsInformation bookings, final List<String> months) throws Exception{
		
		Bookings locations = new Bookings();
		
		Calendar_months calendar  = bookings.getCalendarMonth(0);
    	Days[] days = calendar.getDays();
    	
    	List<String> dates = new ArrayList<String>();
    	List<String> prices = new ArrayList<String>();
    	List<Boolean> availables = new ArrayList<Boolean>();
    	List<Boolean> inserts = new ArrayList<Boolean>();
    
    	for (Days day : days) {
    		List<String> isSavedDay = locations.getBookByIDAndDate(id_location,day.getDate());
    		dates.add(day.getDate());
    		prices.add(day.getPrice().getLocal_price());
    		availables.add(day.getAvailable());
    		inserts.add(Boolean.getBoolean(isSavedDay.get(0)));
    		
    	}
    		
    	locations.saveDataDaysBig(id_location, dates, prices, availables, inserts);
		
	}
	

}
