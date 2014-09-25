package bg.fmi.master.thesis.utils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import bg.fmi.master.thesis.beans.EventTypeBean;
import bg.fmi.master.thesis.models.TEventType;

public class TEventTypeConverter implements Converter {

	private static EventTypeBean eventType = new EventTypeBean();

	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		if (value == null || value.isEmpty()) {
			return "";
		}

		if (!value.matches("\\d+")) {
			throw new ConverterException("The value is not a valid ID number: "
					+ value);
		}

		return eventType.find(value);

	}

	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value == null) {
			return ""; // Or an empty string, can also.
		}

		if (!(value instanceof TEventType)) {
			throw new ConverterException(
					"The value is not a valid TEventType: " + value);
		}

		Long id = ((TEventType) value).getId();
		return (id != null) ? String.valueOf(id) : null;
	}

}
