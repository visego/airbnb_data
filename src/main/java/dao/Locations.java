package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Locations extends Common{
	
	public List<String> selectAllData(String user, String pass, String db_name) throws Exception {
        
		ResultSet out;
		List<String> locationsId = new ArrayList<String>();
		
		try {
			openConnection();
            
            PreparedStatement prep = conexion.prepareStatement("SELECT * FROM locations");
            
            out= prep.executeQuery();
            while (out.next()) {
            	System.out.print("id: " + out.getString(1) + "|  City:" + out.getString(2));
            	System.out.println("|  Latitude: " + out.getString(3) + "|  Longitude:" + out.getString(4) + "|  Description:" + out.getString(5));
            	locationsId.add(out.getString(1));
            	
            }
            closeConnection();
            
            return locationsId;
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Locations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Locations.class.getName()).log(Level.SEVERE, null, ex);
        }
		return null;
    }
	
	public void saveData(final String id, final String city, final String description ,final String latitude, final String longitude, final String type) throws Exception {
        try {
        	openConnection();
            
            final String INSERT_STATEMENT ="INSERT INTO locations (id, city, description, latitude, longitude, type)  VALUES ('%s','%s', '%s','%s','%s','%s')";
            PreparedStatement prepInsert = 
                		conexion.prepareStatement(String.format(INSERT_STATEMENT, id, city, description, latitude, longitude, type));
                
            prepInsert.executeUpdate();
            
            closeConnection();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Locations.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Locations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
	/**
	 * 
	 * @param id identifier of the acommodation
	 * @return boolean, false if the query returns 0 values and 1 if not.
	 * @throws Exception
	 */
	public boolean getDataByID(final String id) throws Exception {
        
		boolean out = false;
		try {
			openConnection();
            
            final String UNIQUE_SELECT = "SELECT * FROM locations WHERE id=%s";
            PreparedStatement prep = conexion.prepareStatement(String.format(UNIQUE_SELECT, id));
            
            int rows = 0;
            ResultSet res = prep.executeQuery();
            if (res.last()) {
                rows = res.getRow();
            }
            if (rows==1){
            	out=true;
            }
            
            closeConnection();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Common.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Common.class.getName()).log(Level.SEVERE, null, ex);
        }
		return out;
    }
	
}
