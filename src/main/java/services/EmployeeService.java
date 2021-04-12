package services;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;


import dao.DAO;
import entities.Employee;
import entities.Employee_;

public class EmployeeService implements Serializable{
	
	
	EntityManager entityManager;
	
	public EmployeeService() {
	}
	
	@PostConstruct
	public void init() {
		entityManager = DAO.entityManager;
	}

	public List<Employee> getEmployees() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Employee> cq =  cb.createQuery(Employee.class);
		Root<Employee> employee = cq.from(Employee.class);
		TypedQuery<Employee> typedQuery=entityManager.createQuery(
				cq.select(employee).orderBy(cb.asc(employee.get(Employee_.id))));
		//TypedQuery<Employee> typeQuery = entityManager.createNamedQuery("Employee.findAllEmployee", Employee.class);
		List<Employee> employees = typedQuery.getResultList();
		employees.forEach(e -> entityManager.refresh(e));
		return employees;
	}
	
	public Employee getEmployeeById(int id) {
//		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//		CriteriaQuery<Employee> cq =  cb.createQuery(Employee.class);
//		Root<Employee> employee = cq.from(Employee.class);
//		TypedQuery<Employee> typedQuery=entityManager.createQuery(
//				cq.select(employee).where(cb.equal(employee.get(Employee_.id), id)));
//		return typedQuery.getSingleResult();

		return (Employee) entityManager.createNamedQuery("Employee.findEmployeeById").setParameter("id", 1).getSingleResult();
	}

	public Employee addEmployee(Employee employee) {
		Employee employee2 = new Employee(employee.getName(),employee.getAge(),
				employee.getGender(),employee.getDob(),employee.getDepartment());
		if(!entityManager.getTransaction().isActive()) entityManager.getTransaction().begin();
		entityManager.persist(employee2);
		entityManager.getTransaction().commit();
		FacesContext.getCurrentInstance().addMessage("addForm:succeed", new FacesMessage(FacesMessage.SEVERITY_INFO, "succeed", "add succeed"));
		return employee2;
	}

	public void deleteEmployee(Employee employee) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaDelete<Employee> delete =  cb.createCriteriaDelete(Employee.class);
		Root<Employee> employeeRoot = delete.from(Employee.class);
		Query query = entityManager.createQuery(
				delete.where(cb.equal(employeeRoot.get(Employee_.id), employee.getId())));
		
		if(!entityManager.getTransaction().isActive()) entityManager.getTransaction().begin();
		query.executeUpdate();
		entityManager.getTransaction().commit();
		FacesContext.getCurrentInstance().addMessage("addForm:succeed", new FacesMessage(FacesMessage.SEVERITY_ERROR, "succeed", "delete succeed"));
	}

	public void editEmployee(Employee employee) {
		employee.setCanEdit(true);
	}

	public void saveEmployees(List<Employee> employees) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaUpdate<Employee> cu = cb.createCriteriaUpdate(Employee.class);
		Root<Employee> employeeRoot = cu.from(Employee.class);
		
		if(!entityManager.getTransaction().isActive()) entityManager.getTransaction().begin();

		for(Employee employee: employees ) {
			cu.set(employeeRoot.get(Employee_.name), employee.getName());
			cu.set(employeeRoot.get(Employee_.age), employee.getAge());
			cu.set(employeeRoot.get(Employee_.gender), employee.getGender());
			cu.set(employeeRoot.get(Employee_.dob), employee.getDob());
			cu.set(employeeRoot.get(Employee_.department), employee.getDepartment());
			cu.where(cb.equal(employeeRoot.get(Employee_.id), employee.getId()));
			entityManager.createQuery(cu).executeUpdate();
			employee.setCanEdit(false);
		}
		entityManager.getTransaction().commit();
	}

	public void cancelUpdate(List<Employee> employees) {
		for (Employee employee : employees) {
			employee.setCanEdit(false);
		}
	}

	
}
