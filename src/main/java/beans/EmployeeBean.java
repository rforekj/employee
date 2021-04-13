package beans;


import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import entities.Employee;
import services.EmployeeService;

@Named
@SessionScoped
public class EmployeeBean implements Serializable{
	private Employee addedEmployee;
	private List<Employee> employees;
	private List<Employee> filterEmployees;

	private boolean selectForm =true; 

	@Inject
	EmployeeService employeeService;
	
	@Inject
	DepartmentBean departmentBean;

	@PostConstruct
	public void init() {
		employees = employeeService.getEmployees();
		addedEmployee = new Employee();
	}
	
	public void destroy() {
	}

	public void addEmployee() {
		//employees.add(employeeService.addEmployee(addedEmployee));
		employeeService.addEmployee(addedEmployee);
		fetchEmployees();
	}

	public void deleteEmployee(Employee employee) {
		employeeService.deleteEmployee(employee);
		fetchEmployees();
		//employees.remove(employee);
	}

	public void editEmployee(Employee employee) {
		employeeService.editEmployee(employee);
	}

	public void saveEmployees() {
		employeeService.saveEmployees(employees);
		fetchEmployees();
	}

	public void cancelUpdate() {
		for (Employee employee : employees) {
			employee.setCanEdit(false);
		}
	}
	
	
	public void fetchEmployees() {
		employees = employeeService.getEmployees();
	}
	
	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public Employee getAddedEmployee() {
		return addedEmployee;
	}

	public void setAddedEmployee(Employee addedEmployee) {
		this.addedEmployee = addedEmployee;
	}
	
	

	public List<Employee> getFilterEmployees() {
		return filterEmployees;
	}

	public void setFilterEmployees(List<Employee> filterEmployees) {
		this.filterEmployees = filterEmployees;
	}
	
	public boolean isSelectForm() {
		return selectForm;
	}

	public void setSelectForm(boolean selectForm) {
		if(selectForm) {
			fetchEmployees();
		} else {
			departmentBean.fetchDempartments();
		}
		this.selectForm = selectForm;
	}
	
	

	public void validateAge(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		try {
			if (Integer.parseInt(value.toString()) > 50 || Integer.parseInt(value.toString()) < 5) {
				throw new ValidatorException(new FacesMessage("age must be greater than 5 and lower than 50"));
			}
		} catch (NumberFormatException e) {
			throw new ValidatorException(new FacesMessage("invalid number"));
		}

	}



}

