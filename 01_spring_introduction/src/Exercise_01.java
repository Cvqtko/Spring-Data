import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Exercise_01 {

	static Connection connection = null;
	static String databaseName = "soft_uni";
	static String url = "jdbc:mysql://localhost:3306/" + databaseName;
	static String username = "root";
	static String password = "Lubime7sa@";

	public static void main(String[] args)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		Class.forName("com.mysql.cj.jdbc.Driver");

		connection = DriverManager.getConnection(url, username, password);

		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM employees WHERE salary > ?");
		Scanner scanner = new Scanner(System.in);
		stmt.setDouble(1, Double.parseDouble(scanner.nextLine()));
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString("first_name") + " " + rs.getString("last_name"));
		}
		connection.close();
	}
}
