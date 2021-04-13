package services;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import entities.Department;
import entities.Department_;

@Transactional
public class DepartmentService implements Serializable{
	
	@PersistenceContext(unitName = "EmployeeManagement")
	EntityManager entityManager;
	

	public DepartmentService() {
	}
	
	@PostConstruct
	public void init() {

	}

	public List<Department> getDepartments() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Department> cq =  cb.createQuery(Department.class);
		Root<Department> department = cq.from(Department.class);
		TypedQuery<Department> typedQuery=entityManager.createQuery(
				cq.select(department));
		List<Department> departments = typedQuery.getResultList();
		
		//List<Department> departments = entityManager.createNamedQuery("Department.findAllDepartment").getResultList();
		departments.forEach(d -> entityManager.refresh(d));
		return departments;
	}
	
	public Department getDepartmentByCode(String code) {
//		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//		CriteriaQuery<Department> cq =  cb.createQuery(Department.class);
//		Root<Department> department = cq.from(Department.class);
//		TypedQuery<Department> typedQuery=entityManager.createQuery(
//				cq.select(department).where(cb.equal(department.get(Department_.departmentCode), code)));
//		return typedQuery.getSingleResult();
		
		Department department = entityManager.find(Department.class, code);
		//entityManager.detach(department);
		//department.setDepartmentName("new");
		//entityManager.flush();
		return department;
	}
	
	
	public Department addDepartment(Department department) {
		Department department2 = new Department(department.getDepartmentCode(),department.getDepartmentName(),department.getDepartmentDescription());
		try {
			entityManager.persist(department2);
			FacesContext.getCurrentInstance().addMessage("addFormD:succeed", new FacesMessage(FacesMessage.SEVERITY_INFO, "succeed", "add succeed"));
		} catch (Exception e) {
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "fail", "duplicate department code");
			FacesContext.getCurrentInstance().addMessage("addFormD:succeed",facesMessage);
			return null;
		}
		return department2;
	}

	public void deleteDepartment(Department department) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaDelete<Department> delete =  cb.createCriteriaDelete(Department.class);
		Root<Department> departmentRoot = delete.from(Department.class);
		Query query = entityManager.createQuery(
				delete.where(cb.equal(departmentRoot.get(Department_.departmentCode), department.getDepartmentCode())));	
		query.executeUpdate();		
		FacesContext.getCurrentInstance().addMessage("addFormD:succeed", new FacesMessage(FacesMessage.SEVERITY_ERROR, "succeed", "delete succeed"));
	}

	public void editDepartment(Department department) {
		department.setCanEdit(true);
	}

	public void saveDepartments(List<Department> departments) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaUpdate<Department> cu = cb.createCriteriaUpdate(Department.class);
		Root<Department> departmentRoot = cu.from(Department.class);
		

		for(Department department: departments ) {
			cu.set(departmentRoot.get(Department_.departmentCode), department.getDepartmentCode());
			cu.set(departmentRoot.get(Department_.departmentName), department.getDepartmentName());
			cu.set(departmentRoot.get(Department_.departmentDescription), department.getDepartmentDescription());
			cu.where(cb.equal(departmentRoot.get(Department_.departmentCode), department.getDepartmentCode()));
			entityManager.createQuery(cu).executeUpdate();
			department.setCanEdit(false);
		}

	}

	public void cancelUpdate(List<Department> departments) {
		for (Department department : departments) {
			department.setCanEdit(false);
		}
	}

	
}
