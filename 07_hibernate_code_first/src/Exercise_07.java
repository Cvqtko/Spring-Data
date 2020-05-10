import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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


		Scanner scanner = new Scanner(System.in);
		int villian_id = Integer.parseInt(scanner.nextLine());

		if (!checkIfEntityExist(villian_id, "villains")) {
			System.out.printf("No villain with ID %d exists in the database.", villian_id);
			return;
		}

		System.out.printf("Villain: %s%n", getEntityById(villian_id, "villains"));
		
		getMinionAndAgeByVillainId(villian_id);
		
		connection.close();
	}

	private static void getMinionAndAgeByVillainId(int id) throws SQLException {
		query = "SELECT m.name, m.age FROM minions AS m\r\n" + 
				"JOIN minions_villains mv on m.id = mv.minion_id\r\n" + 
				"WHERE mv.villain_id = ?;";
		statement = connection.prepareStatement(query);
		statement.setInt(1, id);
		resultSet = statement.executeQuery();
		int count = 1;
		
		while(resultSet.next()) {
			System.out.println(String.format("%d. %s %s",count,resultSet.getString("name"),resultSet.getString("age")));
			count++;
		}
	}

	private static String getEntityById(int entityId, String table) throws SQLException{
		query = "SELECT name FROM " + table + " WHERE id = ?";
		statement = connection.prepareStatement(query);
		statement.setInt(1, entityId);
		resultSet = statement.executeQuery();
		return resultSet.next() ? resultSet.getString("name") : null;
	}

	private static boolean checkIfEntityExist(int entityId, String table) throws SQLException {
		query = "SELECT * FROM " + table + " WHERE id = ?";
		statement = connection.prepareStatement(query);
		statement.setInt(1, entityId);
		resultSet = statement.executeQuery();
		return resultSet.next();
	}
}
