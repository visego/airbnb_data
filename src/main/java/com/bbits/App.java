package com.bbits;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.bbits.dto.BookingsInformation;
import com.bbits.dto.Calendar_months;
import com.bbits.dto.Days;

import dao.Locations;

/**
 * @author visego
 *
 */
public class App 
{
	public final static String URL_PAGE = "https://www.airbnb.es/s/Fuengirola--Espa%C3%B1a?page=" ;
	
	/**
	 * Save locations data in BBDD
	 * @param url
	 * @param pages_number
	 * @throws Exception
	 */
	public static void saveLocationsData(String url, int pages_number) throws Exception{
		SearchResponse search= new SearchResponse();
		
        //URL locations of Fuengirola and save in locations table
        List<String> url_locations = search.getLocationsByCity(url, pages_number);
       
        search.saveDataToBBDD(search.getLocationsByCityProcessed(url_locations));    
		
	}
	
	public static void saveBookingData(final String id_location, final String month, final String year, final String number_months) throws Exception{
		BookingResponse booking= new BookingResponse();
    	BookingsInformation bookingInformation = booking.getBookings(id_location, month, year, number_months);
    	
    	//See details of this out in getBookings method
    	if (bookingInformation !=null){
    		List<String> months = new ArrayList<String>();
    	
    		int monthInt = Integer.parseInt(month);
    		final int numberMonthsInt = Integer.parseInt(number_months);
    	
    		for (int i=0; i<numberMonthsInt; i++){
    			monthInt = monthInt + i;
    			months.add(String.valueOf(monthInt));
    		}
    	
    		booking.saveBookingInformation(id_location,bookingInformation, months);
    	}
    	
	}
	
	/**
	 * Show data in console
	 * @throws Exception
	 */
	public static List<String> showData() throws Exception{
		Locations db = new Locations();
		
		return db.selectAllData("perrete","294014lhkadsf124","airbnb");
	}
	
	public static void updateStatistics(final List<String> locations, final String year, final String month) throws Exception{
    	StatisticsController statsController= new StatisticsController();
    	statsController.saveLocationsStatsComplete(locations, year, month);
	}
	
    public static void main( String[] args ) throws Exception
    {     
    	
    	
    	//LOCATIONS
    	//if (args[0]=="1"){
    		//showData();
            //saveLocationsData(URL_PAGE,19);
    	//}else{
    		//BOOKINGS 
            String month = "06";
            String year = "2016";
            String number_months="1";
        	
        	List<String> locationsId = showData();
        	System.out.println("FIN");
        	
        	for (String locationId : locationsId) {
    			saveBookingData(locationId,month, year, number_months);
    		}
        	
        	//STATISTICS
        	updateStatistics(locationsId, year, month);
    	//}
    	
        
    	
    	
    	

          
    }
}
