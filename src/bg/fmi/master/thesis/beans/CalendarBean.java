package bg.fmi.master.thesis.beans;

import java.util.Date;  

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "calendarBean")
@SessionScoped
public class CalendarBean {  
  
    private Date date1=null;  
      
    public Date getDate1() {  
        return date1;  
    }  
  
    public void setDate1(Date date1) {  
        this.date1 = date1;  
    }  
}  