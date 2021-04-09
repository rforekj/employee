package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import entities.Department;
import entities.Employee;

public class DAO {
	private static final String PERSISTENCE_UNIT_NAME = "EmployeeManagerment";   
    public static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    public static EntityManager entityManager = entityManagerFactory.createEntityManager();
  
}
