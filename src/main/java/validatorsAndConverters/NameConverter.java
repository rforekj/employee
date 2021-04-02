package validatorsAndConverters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("nameConverter")
public class NameConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if (arg2 == null || arg2.trim().isEmpty()) {
	        return null;
	    }
		String result = "";
		String[] list = arg2.split(" ");
		for(int i=0;i<list.length;i++) {
			result+=list[i].substring(0,1).toUpperCase()+list[i].substring(1).toLowerCase()+" ";
		}
		return result;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg2 == null) {
	        return null;
	    }
		String result = "";
		String[] list = arg2.toString().split(" ");
		for(int i=0;i<list.length;i++) {
			result+=list[i].substring(0,1).toUpperCase()+list[i].substring(1).toLowerCase()+" ";
		}
		return result;
	}

}
