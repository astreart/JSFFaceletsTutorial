package bg.fmi.master.thesis.beans;

import java.io.Serializable;
import java.util.Date;  

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "calendarBean")
@SessionScoped
public class CalendarBean implements Serializable {  

	private static final long serialVersionUID = 1L;
	private Date date=null;  
      
    public Date getDate() {  
        return date;  
    }  
  
    public void setDate(Date date) {  
        this.date = date;  
    }  
}  