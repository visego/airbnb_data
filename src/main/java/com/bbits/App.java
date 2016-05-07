package com.bbits;

import java.io.IOException;
import java.util.List;

import bbdd.MySQL;

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
	
	/**
	 * Show data in console
	 * @throws Exception
	 */
	public static void showData() throws Exception{
		MySQL db = new MySQL();
		db.selectAllData("user","pass","bbdd");
	}
	
    public static void main( String[] args ) throws Exception
    {     
    	
    	showData();
    	
        saveLocationsData(URL_PAGE,19);
        
        //TODO
        //BookingResponse booking= new BookingResponse();
    	//booking.getBookings("10587906", "06", "2016", "1");
          
    }
}
