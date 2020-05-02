import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Exercise_08 {

	static Connection connection = null;
	static String databaseName = "minions_db";
	static String url = "jdbc:mysql://localhost:3306/" + databaseName;
	static String username = "root";
	static String password = "root";
	static String query = "";
	static PreparedStatement statement = null;
	static ResultSet resultSet = null;
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		Class.forName("com.mysql.cj.jdbc.Driver");

		connection = DriverManager.getConnection(url, username, password);

		Integer[] minionsId = Arrays.stream(scanner.nextLine().split("\\s+")).map(Integer::parseInt)
				.toArray(Integer[]::new);
		incrementAge(minionsId);
		changeNameToLower(minionsId);
		printAllMinions();
	}

	private static void printAllMinions() throws SQLException {
		query = "SELECT * FROM minions";
		statement = connection.prepareStatement(query);
		resultSet = statement.executeQuery();
		while (resultSet.next()) {
			System.out.println(String.format("%s %d", resultSet.getString("name"), resultSet.getInt("age")));
		}
	}

	private static void changeNameToLower(Integer[] minionsId) throws SQLException {
		for (int i = 0; i < minionsId.length; i++) {
			query = "SELECT * FROM minions WHERE id = ?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, minionsId[i]);
			resultSet = statement.executeQuery();

			resultSet.next();
			query = "UPDATE minions SET name = LCASE(?) WHERE name = ?;";
			statement = connection.prepareStatement(query);
			statement.setString(1, resultSet.getString("name"));
			statement.setString(2, resultSet.getString("name"));
			statement.executeUpdate();
		}

	}

	private static void incrementAge(Integer[] minionsId) throws SQLException {
		for (int i = 0; i < minionsId.length; i++) {
			query = "UPDATE minions SET age = age+1 WHERE id = ?;";
			statement = connection.prepareStatement(query);
			statement.setInt(1, minionsId[i]);
			statement.executeUpdate();
		}
	}
}
