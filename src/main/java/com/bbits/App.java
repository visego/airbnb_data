package com.bbits;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
	private static Logger log = Logger.getAnonymousLogger();
	
	public final static String URL_PAGE = "https://www.airbnb.es/s/Fuengirola--Espa%C3%B1a?page=" ;
	
	/**
	 * Save locations data in BBDD
	 * @param url
	 * @param pages_number
	 * @throws Exception
	 */
	//TODO No hardcoded city and pages_number
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
	
	public static void saveBookingDataComplete(final String id_location, final String month, final String year, final String number_months) throws Exception{
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
    	
    		booking.saveBookingInformationComplete(id_location, bookingInformation, months);
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
	
	/**
	 * 
	 * @param locations id of locations to generate statistics
	 * @param year year of the date to generate statistics
	 * @param month year of the date to generate statistics
	 * @throws Exception
	 */
	public static void updateStatistics(final List<String> locations, final String year, final String month) throws Exception{
    	StatisticsController statsController= new StatisticsController();
    	statsController.saveLocationsStatsComplete(locations, year, month);
	}
	
    public static void main( String[] args ) throws Exception
    {     

        log.info("mensaje de info");
    	
    		//LOCATIONS
//     		showData();
//            saveLocationsData(URL_PAGE,19);
    		//BOOKINGS DATA UPDATES (NO INITIAL LOAD)
            String month = "06";
            String year = "2016";
            String number_months="6";
//        	
        	List<String> locationsId = showData();
        	System.out.println("FIN");
//        	
        	int counter = 0;
        	for (String locationId : locationsId) {
    			saveBookingData(locationId,month, year, number_months);
    			
    			counter++;
    			System.out.println(String.format("%s locations processed of %d", counter, locationsId.size()));
    		}
        	
        	
        	//BOOKINGS COMPLETE INITIAL LOAD
        	
        	//final String locationId = "12359680";
//        	int counter = 0;
//        	for (String locationId : locationsId) {
//        		saveBookingDataComplete(locationId, month, year, number_months);
////    			
//    			counter++;
//    			System.out.println(String.format("%s locations processed of %d", counter, locationsId.size()));
//    		}
        	
        	
        	
        	
//        	STATISTICS
        	updateStatistics(locationsId, year, month);
    	//}
    	
        
    	
    	
    	

          
    }
}
