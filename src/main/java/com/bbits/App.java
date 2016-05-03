package com.bbits;

import java.io.IOException;
import java.util.List;

/**
 * @author visego
 *
 */
public class App 
{
	public final static String URL_PAGE = "https://www.airbnb.es/s/Fuengirola--Espa%C3%B1a?page=" ;
	
    public static void main( String[] args ) throws Exception
    {       
        SearchResponse search= new SearchResponse();
        
        // URL locations of Fuengirola and save in locations table
        List<String> url_locations = search.getLocationsByCity(URL_PAGE, 19);
        
        search.saveDataToBBDD(search.getLocationsByCityProcessed(url_locations));     
        
        // BookingResponse booking= new BookingResponse();
        
        // booking.getBookings("10587906", "04", "2016", "3");
          
    }
}
