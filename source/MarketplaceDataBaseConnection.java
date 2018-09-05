// Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment.
//
// nschowdh

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
/**
*  connect to MySQL database.
*
*/
public class MarketplaceDataBaseConnection {
	private static Connection conn;
	public Connection get_Connection ()
	{
		return conn;
	}

	public MarketplaceDataBaseConnection() {
		//Default Constructor
	}

	public static void dbInit() {
		// Sample Database Connection
		String hostname = "localhost:3306";
		String dbName = "nschowdh_db";
		
		String url = "jdbc:mysql://" + hostname + "/" + dbName;
		String username = "nschowdh";
		String password = "nschowdh";

		System.out.println("Connecting database...");
		
		try {
			conn = (Connection) DriverManager.getConnection(url, username, password);
				
		    System.out.println("Database connected!");
		} catch (SQLException e) {
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
}