import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
	
	static Connection connection = null;
	static String databaseName = "";
	static String url = "jdbc:mysql://localhost:3306/" + databaseName;
	static String username = "root";
	static String password = "Lubime7sa@";
	
	public static void main(String[] args)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
	
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	
			connection = DriverManager.getConnection(url,username,password);

	
	}
}
