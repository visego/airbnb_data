package com.bbits;

import java.util.List;

import dao.Bookings;
import dao.Statistics;

/**
 * Class to generate statistics about data of locations and bookings
 * 
 */
public class StatisticsController {

	Statistics stats;
	
	final String DATE_YEAR_MONTH = "%s-%s";
	
	public void saveLocationsStatsComplete(final List<String> id_locations, final String year, final String month) throws Exception{
		for (String id_location : id_locations) {
			saveLocationStats(id_location,year,month);
			System.out.println(String.format("Saved or updated data for %s location and date %s-%s", id_location,year,month));
		}
	}
	
	public void saveLocationStats(final String id_location, final String year, final String month) throws Exception{
		
		Bookings bookings = new Bookings();
		Statistics stats = new Statistics();
		
		List<String> infoLocation = bookings.getBookingsByLocationAndDate(id_location, year, month);
		
		final String date = String.format(DATE_YEAR_MONTH, year, month);
		
		if (stats.getDataByIDAndMonth(id_location, date).get(0).equals(Boolean.toString(false))){
			stats.saveMonthInformation(id_location,date,infoLocation.get(0), infoLocation.get(1),true);
		}else{
			stats.saveMonthInformation(id_location,date,infoLocation.get(0), infoLocation.get(1),false);
		}
		
		
	}
}
