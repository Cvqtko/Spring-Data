import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Exercise_09 {

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

		int minionId = Integer.parseInt(scanner.nextLine());
		
		query = "CALL usp_get_older(?)";
		CallableStatement callableStatement = connection.prepareCall(query);
		callableStatement.setInt(1, minionId);
		
		callableStatement.executeQuery();
	}
}
