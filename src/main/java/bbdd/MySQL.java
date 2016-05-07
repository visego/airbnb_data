package bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MySQL {
	
	private static Connection conexion;
	
	public void selectAllData(String user, String pass, String db_name) throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            //Local connection
            //conexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db_name, user, pass);
            //Remote connection
            conexion = (Connection) DriverManager.getConnection("jdbc:mysql://air.bbits.es:3306/" + db_name, user, pass);
            
            PreparedStatement prep = conexion.prepareStatement("SELECT * FROM locations");
            
            ResultSet res = prep.executeQuery();
            while (res.next()) {
            	System.out.print("id: " + res.getString(1) + "|  City:" + res.getString(2));
            	System.out.println("|  Latitude: " + res.getString(3) + "|  Longitude:" + res.getString(4) + "|  Description:" + res.getString(5));
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
	public void saveData(final String id, final String city, final String description ,final String latitude, final String longitude) throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            //Local connection
            //conexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "airbnb", "root", "");
            //Remote connection
            conexion = (Connection) DriverManager.getConnection("jdbc:mysql://air.bbits.es:3306/" + "bbdd", "user", "pass");
            
            
            final String INSERT_STATEMENT ="INSERT INTO locations (id, city, description, latitude, longitude)  VALUES ('%s','%s', '%s','%s','%s')";
            PreparedStatement prepInsert = 
                		conexion.prepareStatement(String.format(INSERT_STATEMENT, id, city, description, latitude, longitude));
                
            prepInsert.executeUpdate();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
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
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            //Local connection
            //conexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "airbnb", "root", "");
            //Remote connection
            conexion = (Connection) DriverManager.getConnection("jdbc:mysql://air.bbits.es:3306/" + "bbdd", "user", "pass");
            
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

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
		return out;
    }
}
