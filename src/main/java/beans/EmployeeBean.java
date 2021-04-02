package beans;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.inject.Named;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import entities.Employee;
import services.EmployeeService;

@Named
@SessionScoped
public class EmployeeBean implements Serializable{
	private Employee addedEmployee;
	private List<Employee> employees;

	@Inject
	EmployeeService employeeService;

	@PostConstruct
	public void init() {
		employees = employeeService.getEmployees();
		addedEmployee = new Employee();
		
//		sortBy = new ArrayList<>();
//        sortBy.add(SortMeta.builder()
//                .field("name")
//                .order(SortOrder.ASCENDING)
//                .build());
//
//        sortBy.add(SortMeta.builder()
//                .field("category")
//                .order(SortOrder.ASCENDING)
//                .priority(1)
//                .build());
	}
	
	@PreDestroy
	public void destroy() {
		
	}

	public void addEmployee() {
		Employee employee = new Employee(addedEmployee.getName(), addedEmployee.getAge(), addedEmployee.getGender(),
				addedEmployee.getDob());
		employeeService.addEmployee(employee);
		employees = employeeService.getEmployees();
	}

	public void deleteEmployee(Employee employee) {
		employeeService.deleteEmployee(employee);
		employees = employeeService.getEmployees();
	}

	public void editEmployee(Employee employee) {
		employeeService.editEmployee(employee);
		employees = employeeService.getEmployees();
	}

	public void saveEmployees() {
		employeeService.saveEmployees();
		employees = employeeService.getEmployees();
	}

	public void cancelUpdate() {
		employeeService.cancelUpdate();
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
