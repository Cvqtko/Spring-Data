import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Exercise_02 {

	static Connection connection = null;
	static String databaseName = "diablo";
	static String url = "jdbc:mysql://localhost:3306/" + databaseName;
	static String username = "root";
	static String password = "Lubime7sa@";

	public static void main(String[] args)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		Class.forName("com.mysql.cj.jdbc.Driver");

		connection = DriverManager.getConnection(url, username, password);

		PreparedStatement stmt = connection
				.prepareStatement("SELECT u.first_name, u.last_name, count(ug.game_id)\r\n" + "FROM users as u\r\n"
						+ "INNER JOIN users_games as ug ON u.id=ug.user_id\r\n" + "WHERE u.user_name = ?;");
		Scanner scanner = new Scanner(System.in);
		String user = scanner.nextLine();
		stmt.setString(1, user);
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			String username = rs.getString("first_name");
			if (username != null) {
				System.out.printf("User: %s\r\n%s %s has played %d games\r\n", user, rs.getString("first_name"),
						rs.getString("last_name"), rs.getInt(3));

			} else {
				System.out.println("No such user exists");
			}
		}

		connection.close();
	}
}
