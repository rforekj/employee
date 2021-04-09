package validatorsAndConverters;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

import entities.Department;
import services.DepartmentService;

//@FacesConverter(forClass = Department.class)
@Named
public class DepartmentConverter implements Converter{
	
	@Inject DepartmentService departmentService;
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String submittedValue) {
		if (submittedValue == null || submittedValue.isEmpty()) {
	        return null;
	    }

	    try {
	        return departmentService.getDepartmentByCode(submittedValue);
	    } catch (Exception e) {
	        throw new ConverterException(new FacesMessage(submittedValue + " is b not a valid department code"));
	    }
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object modelValue) {
		
		if (modelValue == null||modelValue.toString().trim().isEmpty()) {
	        return "";
	    }
	    if (modelValue instanceof Department) {
	        return ((Department) modelValue).getDepartmentCode();
	    } else {
	        throw new ConverterException(new FacesMessage(modelValue + " is a not a valid Department"));
	    }
	}

}
