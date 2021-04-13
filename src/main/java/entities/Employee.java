package entities;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
@NamedQueries({
	@NamedQuery(name = "Employee.findAllEmployee", query = "select e from Employee e"),
	@NamedQuery(name = "Employee.findEmployeeById", query = "select e from Employee e where e.id = :id")
})
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence-generator")
	@SequenceGenerator(name = "sequence-generator", sequenceName = "mysequence",allocationSize = 1)
	private int id;
	private String name;
	private Integer age;
	private Gender gender;

	@Temporal(TemporalType.DATE)
	private Date dob;
	
	@Transient
	private boolean canEdit = false;
	
	@ManyToOne
	@JoinColumn(name = "department_code", referencedColumnName = "department_code")
	private Department department;
	
	@Version
	private Integer version;

	public Employee() {
	}

	public Employee(String name, Integer age, Gender gender, Date dob, Department department) {
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.dob = dob;
		this.department = department;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public boolean isCanEdit() {
		return canEdit;
	}

	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}



	public enum Gender {
		MALE, FEMALE
	}

	
}
