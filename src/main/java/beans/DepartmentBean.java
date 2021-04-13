package beans;


import javax.inject.Inject;
import javax.inject.Named;

import entities.Department;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;

import services.DepartmentService;

@Named
//@Stateless
@SessionScoped
public class DepartmentBean implements Serializable{
	private Department addedDepartment;
	private List<Department> departments;
	private List<Department> filterDepartments;

	@Inject
	DepartmentService departmentService;

	@PostConstruct
	public void init() {		
		departments = departmentService.getDepartments();
		addedDepartment = new Department();
	}
	
	@PreDestroy
	public void destroy() {
		System.out.println("conversation destroy");
	}

	public void addDepartment() {
		Department department = departmentService.addDepartment(addedDepartment);
		if(department!=null) departments.add(department);
	}

	public void deleteDepartment(Department department) {
		departmentService.deleteDepartment(department);
		departments.remove(department);
	}

	public void editDepartment(Department department) {
		departmentService.editDepartment(department);
	}

	public void saveDepartments() {
		departmentService.saveDepartments(departments);
	}

	public void cancelUpdate() {
		for (Department department : departments) {
			department.setCanEdit(false);
		}
	}
	
	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public Department getAddedDepartment() {
		return addedDepartment;
	}

	public void setAddedDepartment(Department addedDepartment) {
		this.addedDepartment = addedDepartment;
	}
	
	

	public List<Department> getFilterDepartments() {
		return filterDepartments;
	}

	public void setFilterDepartments(List<Department> filterDepartments) {
		this.filterDepartments = filterDepartments;
	}



}

