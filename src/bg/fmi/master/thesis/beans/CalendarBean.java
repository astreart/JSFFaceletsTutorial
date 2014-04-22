package bg.fmi.master.thesis.beans;

import java.util.Date;  

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "calendarBean")
@SessionScoped
public class CalendarBean {  
  
    private Date date=null;  
      
    public Date getDate() {  
        return date;  
    }  
  
    public void setDate(Date date) {  
        this.date = date;  
    }  
}  