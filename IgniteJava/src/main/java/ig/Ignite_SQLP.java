package ig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import clojure.main;

public class Ignite_SQLP {
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/IGNITE_1","root","root");
			try (Statement stmt = conn.createStatement()) {

			    // Create table based on REPLICATED template.
			    stmt.executeUpdate("CREATE TABLE City (" + 
			    " id int PRIMARY KEY, name VARCHAR(25)) ");
			    System.out.println("Created");
			    // Create table based on PARTITIONED template with one backup.
			    stmt.executeUpdate("CREATE TABLE Person (" +
			    " id int, name VARCHAR(25), city_id int, " +
			    " PRIMARY KEY (id, city_id)) " );
			    System.out.println("Created2");
			  
			    // Create an index on the City table.
			    stmt.executeUpdate("CREATE INDEX idx_city_name ON City (name)");

			    // Create an index on the Person table.
			    stmt.executeUpdate("CREATE INDEX idx_person_name ON Person (name)");
			    System.out.println("Done");
			}
		
	}
}
