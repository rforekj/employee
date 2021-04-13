package entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.Version;



@Entity
public class Department {

	@Id
	@Column(name = "department_code")
	private String departmentCode;
	
	@Column(name = "department_name")
	private String departmentName;
	
	@Column(name = "department_description")
	private String departmentDescription;
	
	@OneToMany(mappedBy = "department",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Employee> employeeList;
	
	@Transient
	private boolean canEdit = false;
	
	@Version
	private Integer version;
	
	public Department() {
		// TODO Auto-generated constructor stub
	}
	

	public Department(String departmentCode, String departmentName, String departmentDescription) {
		super();
		this.departmentCode = departmentCode;
		this.departmentName = departmentName;
		this.departmentDescription = departmentDescription;
	}
	
	public String getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getDepartmentDescription() {
		return departmentDescription;
	}
	public void setDepartmentDescription(String departmentDescription) {
		this.departmentDescription = departmentDescription;
	}
	
	public List<Employee> getEmployeeList() {
		return employeeList;
	}
	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}
	public boolean isCanEdit() {
		return canEdit;
	}
	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}


	@Override
    public boolean equals(Object other) {
        return (other != null && getClass() == other.getClass() && departmentCode!= null)
            ? departmentCode.equals(((Department) other).departmentCode)
            : (other == this);
    }

    @Override
    public int hashCode() {
        return (departmentCode != null) 
            ? (getClass().hashCode() + departmentCode.hashCode())
            : super.hashCode();
    }

	
	
}
