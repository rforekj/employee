package entities;

import java.util.Date;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import entities.Employee.Gender;

@StaticMetamodel(Employee.class)
public class Employee_ {
	
	  public static volatile SingularAttribute<Employee, Integer> id;
	  public static volatile SingularAttribute<Employee, String> name;
	  public static volatile SingularAttribute<Employee, Integer> age;
	  public static volatile SingularAttribute<Employee, Gender> gender;
	  public static volatile SingularAttribute<Employee, Date> dob;
	  
	  public static volatile SingularAttribute<Employee, Department> department;
}
