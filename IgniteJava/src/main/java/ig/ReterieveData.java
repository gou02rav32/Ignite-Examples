package ig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import clojure.main;

public class ReterieveData {
	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/IGNITE_1","root","root");
		
		try (Statement stmt = conn.createStatement()) {
		    try (ResultSet rs = stmt.executeQuery("SELECT p.name, c.name " + " FROM Person p, City c " + " WHERE p.city_id = c.id")) {

		      while (rs.next())
		         System.out.println(rs.getString(1) + ", " + rs.getString(2));
		    }
	}
	}
}

