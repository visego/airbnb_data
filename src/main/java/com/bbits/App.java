package com.bbits;

import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
	public final static String URL_PAGE = "https://www.airbnb.es/s/Fuengirola--Espa%C3%B1a?page=" ;
	
    public static void main( String[] args ) throws IOException
    {       
        SearchResponse search= new SearchResponse();
        
        BookingResponse booking= new BookingResponse();
        
        List<String> url_locations = search.getLocationsByCity(URL_PAGE, 19);
        
        search.getLocationsByCityProcessed(url_locations);
              
        booking.getBookings("10587906", "04", "2016", "3");
          
    }
}
