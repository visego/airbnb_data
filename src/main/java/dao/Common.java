package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Common {
	
	
	protected static Connection conexion;
	
	protected void openConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		
//		if (!conexion.isValid(0)){
			Class.forName("com.mysql.jdbc.Driver").newInstance();
	        //Local connection
	        //conexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "airbnb", "root", "");
	        //Remote connection
	        conexion = (Connection) DriverManager.getConnection("jdbc:mysql://air.bbits.es:3306/" + "airbnb", "perrete", "294014lhkadsf124");
//		}
	}
	
	protected void closeConnection() throws SQLException{
		conexion.close();
	}
}
