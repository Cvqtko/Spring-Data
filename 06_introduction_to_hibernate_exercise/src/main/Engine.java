package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import main.entities.Employee;
import main.entities.Town;

public class Engine implements Runnable {

	private final EntityManager entityManager;
	private final BufferedReader reader;

	public Engine(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.reader = new BufferedReader(new InputStreamReader(System.in));
	}

	@Override
	public void run() {
		// Ex. 2
		// this.removeObjectsEx();

		// Ex. 3
		/*
		 * try { this.containsEmployeeEx(); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

		// Ex. 4
		this.employeeWithSalaryOver50000();

	}

	private void employeeWithSalaryOver50000() {
		List<Employee> employees = this.entityManager.createQuery("SELECT e FROM Employee AS e WHERE e.salary > 50000",
				Employee.class).getResultList();
		
		employees.forEach(e->System.out.println(e.getFirstName()));
		
	}

	private void containsEmployeeEx() throws IOException {
		System.out.println("Enter employee full name");
		String fullName = this.reader.readLine();

		try {
			Employee employee = this.entityManager
					.createQuery("SELECT e FROM Employee AS e WHERE concat(e.firstName, ' ',e.lastName) = :name",
							Employee.class)
					.setParameter("name", fullName).getSingleResult();
			System.out.println("Yes");
		} catch (NoResultException ex) {
			System.out.println("No");
		}
	}

	private void removeObjectsEx() {
		List<Town> towns = this.entityManager.createQuery("SELECT t FROM Town as t WHERE length(t.name)>5", Town.class)
				.getResultList();
		this.entityManager.getTransaction().begin();
		towns.forEach(t -> this.entityManager.detach(t));

		for (Town town : towns) {
			town.setName(town.getName().toLowerCase());
		}

		towns.forEach(t -> this.entityManager.merge(t));

		this.entityManager.getTransaction().commit();
	}
}
