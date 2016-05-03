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
            conexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db_name, user, pass);
            
            PreparedStatement prep = conexion.prepareStatement("SELECT * FROM locations");
            
            ResultSet res = prep.executeQuery();
//            while (res.next()) {
//            	System.out.print("id: " + res.getString(1) + "|  City:" + res.getString(2));
//            	System.out.println("|  Latitud: " + res.getString(3) + "|  Longitude:" + res.getString(4));
//            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
	public void saveData(final String id, final String city, final String description ,final String latitude, final String longitude) throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "airbnb", "root", "");
            
            final String INSERT_STATEMENT ="INSERT INTO locations (id, city, description, latitude, longitude)  VALUES ('%s','%s', '%s','%s','%s')";
            PreparedStatement prep = 
            		conexion.prepareStatement(String.format(INSERT_STATEMENT, id, city, description, latitude, longitude));
            
            prep.executeUpdate();    

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
