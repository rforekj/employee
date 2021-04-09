package services;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;


import dao.DAO;
import entities.Department;
import entities.Department_;

public class DepartmentService implements Serializable{
	
	
	EntityManager entityManager;
	
	public DepartmentService() {
	}
	
	@PostConstruct
	public void init() {
		entityManager = DAO.entityManager;
	}

	public List<Department> getDepartments() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Department> cq =  cb.createQuery(Department.class);
		Root<Department> department = cq.from(Department.class);
		TypedQuery<Department> typedQuery=entityManager.createQuery(
				cq.select(department));
		List<Department> departments = typedQuery.getResultList();
		departments.forEach(d -> entityManager.refresh(d));
		return departments;
	}
	
	public Department getDepartmentByCode(String code) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Department> cq =  cb.createQuery(Department.class);
		Root<Department> department = cq.from(Department.class);
		TypedQuery<Department> typedQuery=entityManager.createQuery(
				cq.select(department).where(cb.equal(department.get(Department_.departmentCode), code)));
		return typedQuery.getSingleResult();
	}

	public Department addDepartment(Department department) {
		Department department2 = new Department(department.getDepartmentCode(),department.getDepartmentName(),department.getDepartmentDescription());
		try {
			if(!entityManager.getTransaction().isActive()) entityManager.getTransaction().begin();
			entityManager.persist(department2);
			entityManager.getTransaction().commit();
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
		
		if(!entityManager.getTransaction().isActive()) entityManager.getTransaction().begin();
		query.executeUpdate();
		entityManager.getTransaction().commit();
		
		FacesContext.getCurrentInstance().addMessage("addFormD:succeed", new FacesMessage(FacesMessage.SEVERITY_ERROR, "succeed", "delete succeed"));
	}

	public void editDepartment(Department department) {
		department.setCanEdit(true);
	}

	public void saveDepartments(List<Department> departments) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaUpdate<Department> cu = cb.createCriteriaUpdate(Department.class);
		Root<Department> departmentRoot = cu.from(Department.class);
		
		if(!entityManager.getTransaction().isActive()) entityManager.getTransaction().begin();

		for(Department department: departments ) {
			cu.set(departmentRoot.get(Department_.departmentCode), department.getDepartmentCode());
			cu.set(departmentRoot.get(Department_.departmentName), department.getDepartmentName());
			cu.set(departmentRoot.get(Department_.departmentDescription), department.getDepartmentDescription());
			cu.where(cb.equal(departmentRoot.get(Department_.departmentCode), department.getDepartmentCode()));
			entityManager.createQuery(cu).executeUpdate();
			department.setCanEdit(false);
		}
		entityManager.getTransaction().commit();

	}

	public void cancelUpdate(List<Department> departments) {
		for (Department department : departments) {
			department.setCanEdit(false);
		}
	}

	
}
