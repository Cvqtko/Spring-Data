import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import entities.User;
import orm.Connector;
import orm.EntityManager;

public class Main {
	public static void main(String[] args) throws SQLException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {

		Connector connector = new Connector();
		connector.getConnection();
		String username = "root";
		String password = "root";

		Connector.createConnection(username, password, "orm_db");

		EntityManager<User> entityManager = new EntityManager<>(Connector.getConnection());

		User user = new User("Sasho", "12345", 21, new Date());
		User user2 = new User("Stamat", "12345", 22, new Date());
		entityManager.persist(user2);

		User user3 = entityManager.findFirst(User.class, " username = 'Stamat'");
		entityManager.delete(user3);

		System.out.println("User is deleted");

		List<User> users = (List<User>) entityManager.find(User.class, " age > 23 ");

		for (User userr : users) {
			System.out.println(userr.getUsername());
		}

		System.out.println("--------");

		List<User> allusers = (List<User>) entityManager.find(User.class);
		for (User userr : allusers) {
			System.out.println(userr.getUsername());
		}
	}
}
