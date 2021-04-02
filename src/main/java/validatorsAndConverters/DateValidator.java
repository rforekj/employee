package validatorsAndConverters;

import java.text.SimpleDateFormat;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("dateValidator")
public class DateValidator implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");
		try {
			String dateString = simpleDateFormat.format(arg2);
			int day = Integer.parseInt(dateString.substring(0, 2));
			int month = Integer.parseInt(dateString.substring(3, 5));
			int year = Integer.parseInt(dateString.substring(6));
			if (day > 31 || day < 1) {
				FacesMessage facesMessage = new FacesMessage("day is from 1 to 31");
				throw new ValidatorException(facesMessage);
			}
			if (month < 1 || month > 12) {
				FacesMessage facesMessage = new FacesMessage("month is from 1 to 12");
				throw new ValidatorException(facesMessage);
			}
			if (year < 1500 || year > 2050) {
				FacesMessage facesMessage = new FacesMessage("year is from 1500 to 2050");
				throw new ValidatorException(facesMessage);
			}
		} catch (Exception e) {
			FacesMessage facesMessage = new FacesMessage(e.getMessage());
			throw new ValidatorException(facesMessage);
		}

	}

}
