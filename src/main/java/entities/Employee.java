package entities;

import java.util.Date;

import javax.faces.bean.ManagedBean;

public class Employee {
	private String name;
	private Integer age;
	private Gender gender;
	private Date dob;
	private boolean canEdit = false;
	
	public Employee() {
	}

	public Employee(String name, Integer age, Gender gender, Date dob) {
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.dob = dob;
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




	public enum Gender {
		MALE,
		FEMALE
	}
}


