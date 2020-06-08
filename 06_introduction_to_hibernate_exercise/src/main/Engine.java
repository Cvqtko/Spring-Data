package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import main.entities.Address;
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
		 * try { this.containsEmployeeEx(); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */

		// Ex. 4
		// this.employeeWithSalaryOver50000();

		// Ex. 5
		// this.employeeFromDepartmentsEx();

		// Ex. 6

//		  try { this.addingNewAddressAndAddItToEmp(); } catch (IOException e) {
//		  e.printStackTrace(); }
//		 

		// Ex. 7
		this.addressWithEmployeeCount();


	}

	private void addressWithEmployeeCount() {
		List<Address> addresses = entityManager.createQuery("SELECT a FROM Address AS a", Address.class)
				.getResultList();
		addresses.sort((a1, a2) -> a2.getEmployees().size() - a1.getEmployees().size());

		for (int i = 0; i < 10; i++) {
			System.out.printf("%s - %d employees\n", addresses.get(i).getText(),
					addresses.get(i).getEmployees().size());
		}

	}

	private void addingNewAddressAndAddItToEmp() throws IOException {
		System.out.println("Enter employee last name");
		String lastName = this.reader.readLine();

		Employee employee = this.entityManager
				.createQuery("SELECT e FROM Employee AS e WHERE e.lastName = :name", Employee.class)
				.setParameter("name", lastName).getSingleResult();

		Address address = this.createNewAddress("Vitoshka 15");

		this.entityManager.getTransaction().begin();
		this.entityManager.detach(employee);
		employee.setAddress(address);
		this.entityManager.merge(employee);
		this.entityManager.flush();
		this.entityManager.getTransaction().commit();
		System.out.println();
	}

	private Address createNewAddress(String textContent) {
		Address address = new Address();
		address.setText(textContent);

		this.entityManager.getTransaction().begin();

		this.entityManager.persist(address);
		this.entityManager.getTransaction().commit();

		return address;
	}

	private void employeeFromDepartmentsEx() {
		List<Employee> employees = this.entityManager
				.createQuery("SELECT e FROM Employee AS e WHERE e.department.name = 'Research and Development'"
						+ "ORDER BY e.salary, e.id", Employee.class)
				.getResultList();
		employees.forEach(e -> System.out.printf("%s %s from %s - %.2f$\n", e.getFirstName(), e.getLastName(),
				e.getDepartment(), e.getSalary()));
	}

	private void employeeWithSalaryOver50000() {
		List<Employee> employees = this.entityManager
				.createQuery("SELECT e FROM Employee AS e WHERE e.salary > 50000", Employee.class).getResultList();

		employees.forEach(e -> System.out.println(e.getFirstName()));

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
