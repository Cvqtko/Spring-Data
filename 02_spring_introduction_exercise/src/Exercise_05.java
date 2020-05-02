import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Exercise_05 {

	static Connection connection = null;
	static String databaseName = "minions_db";
	static String url = "jdbc:mysql://localhost:3306/" + databaseName;
	static String username = "root";
	static String password = "root";
	static String query = "";
	static PreparedStatement statement = null;
	static ResultSet resultSet = null;

	public static void main(String[] args)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection(url, username, password);
		Scanner scanner = new Scanner(System.in);
		String country = scanner.nextLine();
		resultSet = getTownsByCountry(country);
		int counter = 0;
		ArrayList<String> townsAffected = new ArrayList();
		while (resultSet.next()) {
			townsAffected.add(resultSet.getString("name").toUpperCase());
			query = "UPDATE towns SET name = UCASE(?) WHERE name = ?;";
			statement = connection.prepareStatement(query);
			statement.setString(1, resultSet.getString("name"));
			statement.setString(2, resultSet.getString("name"));
			counter++;
		}
		if (counter == 0) {
			System.out.println("No town names were affected.");
		} else {
			System.out.printf("%d town names were affected.\n", counter);
			System.out.println(Arrays.toString(townsAffected.toArray()));
		}
		

	}

	private static ResultSet getTownsByCountry(String country) throws SQLException {
		query = "SELECT name FROM towns\r\n" + "WHERE country = ?;";
		statement = connection.prepareStatement(query);
		statement.setString(1, country);
		resultSet = statement.executeQuery();
		return resultSet;
	}
}
