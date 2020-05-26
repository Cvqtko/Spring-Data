import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Exercise_01 {

	static Connection connection = null;
	static String databaseName = "minions_db";
	static String url = "jdbc:mysql://localhost:3306/" + databaseName;
	static String username = "root";
	static String password = "root";

	public static void main(String[] args)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		Class.forName("com.mysql.cj.jdbc.Driver");

		connection = DriverManager.getConnection(url, username, password);

		PreparedStatement stmt = connection.prepareStatement("SELECT v.name, COUNT(mv.minion_id) AS `count`\r\n" + 
				"FROM villains AS v\r\n" + 
				"JOIN minions_villains mv on v.id = mv.villain_id\r\n" + 
				"GROUP BY v.name\r\n" + 
				"HAVING `count` > 15\r\n" + 
				"ORDER BY `count` DESC;");
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString("name") + " " + rs.getString("count"));
		}
		connection.close();
	}
}
