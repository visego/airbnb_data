package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bookings extends Common {
	
	final String DATE_FORMAT = "%s-%s-%s";
	final String FIRST_DAY_MONTH = "01";
	
	public void saveDataDays(final String id_location, final String date, final String price, final boolean available, final boolean insert) throws Exception {
        try {
        	openConnection();
            
        	if (insert){
        		final String INSERT_STATEMENT ="INSERT INTO bookings (id, date, price, available)  VALUES ('%s','%s', '%s','%s')";
                PreparedStatement prepInsert = 
                    		conexion.prepareStatement(String.format(INSERT_STATEMENT, id_location, date, price, available));
                    
                prepInsert.executeUpdate();
        	}else{
        		final String UPDATE_STATEMENT ="UPDATE bookings SET id = '%s', date = '%s', price = '%s', available = '%s'  WHERE id = '%s' AND date = '%s'";
                PreparedStatement prepInsert = 
                    		conexion.prepareStatement(String.format(UPDATE_STATEMENT, id_location, date, price, available, id_location, date));
                
                prepInsert.executeUpdate();
        	}

            closeConnection();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Common.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Common.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
	/**
	 * 
	 * @param id identifier of the acommodation
	 * @return boolean, false if the query returns 0 values and 1 if not.
	 * @throws Exception
	 */
	public List<String> getBookByIDAndDate(final String id, final String date) throws Exception {
        

		List<String> bookInformation = new ArrayList<String>();
		try {
			openConnection();
			
            final String UNIQUE_SELECT = "SELECT * FROM bookings WHERE (id=%s) AND (date='%s')";
            PreparedStatement prep = conexion.prepareStatement(String.format(UNIQUE_SELECT, id, date));
            
            int rows = 0;
            ResultSet res = prep.executeQuery();
            if (res.last()) {
                rows = res.getRow();
            }
            if (rows==1){
            	bookInformation.add(Boolean.toString(true));
            	bookInformation.add(res.getString(3));
                bookInformation.add(res.getString(4));
            }else{
            	bookInformation.add(Boolean.toString(false));
            }
            
            closeConnection();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Common.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Common.class.getName()).log(Level.SEVERE, null, ex);
        }
		return bookInformation;
    }
	
	public List<String> getBookingsByLocationAndDate(final String id, final String year, final String month) throws InstantiationException, IllegalAccessException{
		
		List<String> infoByLocationMonthly = new ArrayList<String>();
		try {
			openConnection();
			
			//January is 0
			final int monthInt = Integer.parseInt(month) -1;
			String lastDayOfMonth = String.valueOf(getMonthDays(Integer.parseInt(year),monthInt));
			
			final String initialDate = String.format(DATE_FORMAT,year,month,FIRST_DAY_MONTH);
			final String lastDate = String.format(DATE_FORMAT,year,month,lastDayOfMonth);
			
            final String SELECT_MULTIPLE = "SELECT * FROM bookings WHERE (id=%s) AND (date>='%s') AND (date<='%s')";
            PreparedStatement prep = conexion.prepareStatement(String.format(SELECT_MULTIPLE, id, initialDate, lastDate));
            
            int rows = 0;
            int price =0;
            double numberOfDays = 0;
            double availableDays = 0;
            
            ResultSet res = prep.executeQuery();
            
            while (res.next()) {
            	numberOfDays++;
            	price += Integer.parseInt(res.getString(3));
            	if (Boolean.parseBoolean(res.getString(4))== true){
            		availableDays++;
            	}
            }
            
            final double price_mean = price/numberOfDays;
            final double percentage_books = ((numberOfDays-availableDays)/numberOfDays)*100;
            
            infoByLocationMonthly.add(String.valueOf(percentage_books));
            infoByLocationMonthly.add(String.valueOf(price_mean));
            
                  
            closeConnection();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Common.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Common.class.getName()).log(Level.SEVERE, null, ex);
        }
		return infoByLocationMonthly;
	}
	
	private int getMonthDays(int year, int month){
		
		Calendar date = new GregorianCalendar(year, month, 1);
		
		return  date.getActualMaximum(Calendar.DAY_OF_MONTH); 
	}

}
