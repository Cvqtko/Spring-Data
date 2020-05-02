import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Exercise_06 {

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
		int villian_id = Integer.parseInt(scanner.nextLine());
		int minionsReleased = 0;
		query = "DELETE FROM minions_villains WHERE villain_id = ?;";
		statement = connection.prepareStatement(query);
		statement.setInt(1, villian_id);
		minionsReleased = statement.executeUpdate();

		query = "SELECT * FROM villains WHERE id = ?";
		statement = connection.prepareStatement(query);
		statement.setInt(1, villian_id);
		resultSet = statement.executeQuery();

		if (resultSet.next()) {
			System.out.printf("%s was deleted \n%d minions released", resultSet.getString("name"), minionsReleased);
			query = "DELETE FROM villains WHERE id = ?";
			statement = connection.prepareStatement(query);
			statement.setInt(1, villian_id);
			statement.executeUpdate();
		} else {
			System.out.println("No such villain was found");
		}

	}

}
