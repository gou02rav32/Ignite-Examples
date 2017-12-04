package ig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataInsertion_1 {
	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/IGNITE_1","root","root");
		
		try (PreparedStatement stmt =
				conn.prepareStatement("INSERT INTO City (id, name) VALUES (?, ?)")) {

				    stmt.setLong(1, 1);
				    stmt.setString(2, "Forest Hill");
				    stmt.executeUpdate();

				    stmt.setLong(1, 2);
				    stmt.setString(2, "Denver");
				    stmt.executeUpdate();

				    stmt.setLong(1, 3);
				    stmt.setString(2, "St. Petersburg");
				    stmt.executeUpdate();
				    System.out.println("Insertion 1 done");
				}

				// Populate Person table
				try (PreparedStatement stmt =
				conn.prepareStatement("INSERT INTO Person (id, name, city_id) VALUES (?, ?, ?)")) {

				    stmt.setLong(1, 1);
				    stmt.setString(2, "John Doe");
				    stmt.setLong(3, 3);
				    stmt.executeUpdate();

				    stmt.setLong(1, 2);
				    stmt.setString(2, "Jane Roe");
				    stmt.setLong(3, 2);
				    stmt.executeUpdate();

				    stmt.setLong(1, 3);
				    stmt.setString(2, "Mary Major");
				    stmt.setLong(3, 1);
				    stmt.executeUpdate();

				    stmt.setLong(1, 4);
				    stmt.setString(2, "Richard Miles");
				    stmt.setLong(3, 2);
				    stmt.executeUpdate();
				    System.out.println("Insertion 2 done");
				}
	}
}
