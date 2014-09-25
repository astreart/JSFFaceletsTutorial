package bg.fmi.master.thesis.utils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import bg.fmi.master.thesis.beans.FilterTypeBean;
import bg.fmi.master.thesis.models.TFilterType;

@FacesConverter("filterTypeConverter")
public class TFilterTypeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
    	// It will create bean when not done yet.
        FilterTypeBean filterTypeBean = context.getApplication().evaluateExpressionGet(context, "#{filterTypeBean}", FilterTypeBean.class);
       
        for (TFilterType type : filterTypeBean.listBooleanFilterTypes()) {
            if (type.getFilterTypeName().equals(value)) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof TFilterType) {
            return ((TFilterType) value).getFilterTypeName();
        } else {
            return "";
        }
    }
}