import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Exercise_04 {

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
		String [] minionParameters = scanner.nextLine().split("\\s+");
		String minionName = minionParameters[1];
		int minionAge = Integer.parseInt(minionParameters[2]);
		String minionTown = minionParameters[3];
		
		String villainName = scanner.nextLine().split("\\s+")[1];
		
		if(!checkIfEntityExistByName(minionTown,"towns"))	{
			insertEntityInTowns(minionTown);
			System.out.println(String.format("Town %s was added to the database.",minionTown));
		}
		if(!checkIfEntityExistByName(villainName,"villains")) {
			insertEntityInVillains(villainName);
			System.out.println(String.format("Villain %s was added to the database.",villainName));
		}
		
		insertMinion(minionName,minionAge,minionTown);
		addMinionToVillain(minionName,villainName);
		System.out.printf("Successfully added %s to be minion of %s.",minionName,villainName);
		
	}

	private static void addMinionToVillain(String minionName, String villainName) throws SQLException {
		query = "INSERT INTO minions_villains (minion_id, villain_id) VALUES ((SELECT id FROM minions WHERE name = ? LIMIT 1), (SELECT id FROM villains WHERE name = ?));";
		statement = connection.prepareStatement(query);
		statement.setString(1, minionName);
		statement.setString(2, villainName);
		statement.execute();
	}

	private static void insertMinion(String minionName, int minionAge, String minionTown) throws SQLException {
		query = "INSERT INTO minions (name, age,town_id) VALUES (?, ?, (SELECT id FROM towns WHERE name = ?))";
		statement = connection.prepareStatement(query);
		statement.setString(1, minionName);
		statement.setInt(2, minionAge);
		statement.setString(3, minionTown);
		statement.execute();
		
	}

	private static void insertEntityInVillains(String villainName) throws SQLException {
		query = "INSERT INTO villains (name, evilness_factor) values(?, ?)";
		statement = connection.prepareStatement(query);
		statement.setString(1, villainName);
		statement.setString(2, "evil");
		statement.execute();
		
	}

	private static void insertEntityInTowns(String minionTown) throws SQLException {
		query = "INSERT INTO towns (name, country) values(?, ?)";
		statement = connection.prepareStatement(query);
		statement.setString(1, minionTown);
		statement.setString(2, "NULL");
		statement.execute();
	}

	private static boolean checkIfEntityExistByName(String entityName, String tableName) throws SQLException {
		query = "SELECT * FROM " + tableName + " WHERE name = ?";
		statement = connection.prepareStatement(query);
		statement.setString(1, entityName);
		resultSet = statement.executeQuery();
		return resultSet.next();
	}

	private static void getMinionAndAgeByVillainId(int id) throws SQLException {
		query = "SELECT m.name, m.age FROM minions AS m\r\n" + "JOIN minions_villains mv on m.id = mv.minion_id\r\n"
				+ "WHERE mv.villain_id = ?;";
		statement = connection.prepareStatement(query);
		statement.setInt(1, id);
		resultSet = statement.executeQuery();
		int count = 1;

		while (resultSet.next()) {
			System.out.println(
					String.format("%d. %s %s", count, resultSet.getString("name"), resultSet.getString("age")));
			count++;
		}
	}

	private static String getEntityById(int entityId, String table) throws SQLException {
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
