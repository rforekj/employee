package entities;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@StaticMetamodel(Department.class)
public class Department_ {
	  public static volatile SingularAttribute<Department, String> departmentCode;
	  public static volatile SingularAttribute<Department, String> departmentName;
	  public static volatile SingularAttribute<Department, String> departmentDescription;
	  
	  public static volatile ListAttribute<Department, Employee> employeeList;
}
