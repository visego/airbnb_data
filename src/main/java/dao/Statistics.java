package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Statistics extends Common {

	public void saveMonthInformation(final String id_location, final String month, final String book, final String price, final boolean insert) throws Exception {
        try {
        	openConnection();
            
        	if (insert){
        		final String INSERT_STATEMENT ="INSERT INTO statistics (id, month, books, price)  VALUES ('%s','%s', '%s','%s')";
                PreparedStatement prepInsert = 
                    		conexion.prepareStatement(String.format(INSERT_STATEMENT, id_location, month, book, price));
                
                prepInsert.executeUpdate();
        	}else{
        		final String UPDATE_STATEMENT ="UPDATE statistics SET id = '%s', month = '%s', books = '%s', price = '%s'  WHERE id = '%s' AND month = '%s'";
                PreparedStatement prepInsert = 
                    		conexion.prepareStatement(String.format(UPDATE_STATEMENT, id_location, month, book, price, id_location, month));
                
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
	 * @param date year and month 
	 * @return boolean, false if there are statistics saved for this acommodation and date.
	 * @throws Exception
	 */
	public List<String> getDataByIDAndMonth(final String id, final String month) throws Exception {
        
		List<String> statsInfo = new ArrayList<String>();
		try {
			openConnection();
            
            final String UNIQUE_SELECT = "SELECT * FROM statistics WHERE id=%s AND month='%s'";
            PreparedStatement prep = conexion.prepareStatement(String.format(UNIQUE_SELECT, id,month));
            
            int rows = 0;
            ResultSet res = prep.executeQuery();
            if (res.last()) {
                rows = res.getRow();
            }
            if (rows==1){
            	statsInfo.add(Boolean.toString(true));
            }else{
            	statsInfo.add(Boolean.toString(false));
            }
        	statsInfo.add(res.getString(3));
        	statsInfo.add(res.getString(4));
            
            closeConnection();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Common.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Common.class.getName()).log(Level.SEVERE, null, ex);
        }
		return statsInfo;
    }
}
