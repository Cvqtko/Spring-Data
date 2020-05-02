import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Scanner;

public class Exercise_07 {

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

		query = "SELECT * FROM minions;";
		statement = connection.prepareStatement(query);
		resultSet = statement.executeQuery();
		LinkedList<String> minions = new LinkedList();

		while (resultSet.next()) {
			minions.add(resultSet.getString("name"));
		}

		for (int i = 0; i < minions.size() / 2; i++) {
			if (i != minions.size() - 1 - i) {
				System.out.println(minions.get(i));
				System.out.println(minions.get(minions.size() - 1 - i));
			} else {
				System.out.println(minions.get(i));
			}
		}
	}
}
