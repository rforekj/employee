package services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import entities.Employee;
import entities.Employee.Gender;

@SessionScoped
public class EmployeeService implements Serializable{
	private List<Employee> employees;

	public EmployeeService() {
		employees = new ArrayList<Employee>(Arrays.asList(
				new Employee("John", 19, Gender.MALE, new Date()),
				new Employee("Mack", 20, Gender.MALE, new Date()),
				new Employee("Bran", 22, Gender.FEMALE, new Date()),
				new Employee("Sole", 28, Gender.FEMALE, new Date()),
				new Employee("Yama", 17, Gender.FEMALE, new Date()),
				new Employee("Cin", 23, Gender.MALE, new Date()),
				new Employee("Tale", 20, Gender.FEMALE, new Date())));
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void addEmployee(Employee employee) {
		employees.add(employee);
		FacesContext.getCurrentInstance().addMessage("addForm:succeed", new FacesMessage(FacesMessage.SEVERITY_INFO, "succeed", "add succeed"));
	}

	public void deleteEmployee(Employee employee) {
		employees.remove(employee);
	}

	public void editEmployee(Employee employee) {
		employee.setCanEdit(true);
	}

	public void saveEmployees() {
		for (Employee employee : employees) {
			employee.setCanEdit(false);
		}
	}

	public void cancelUpdate() {
		for (Employee employee : employees) {
			employee.setCanEdit(false);
		}
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	
	
}
