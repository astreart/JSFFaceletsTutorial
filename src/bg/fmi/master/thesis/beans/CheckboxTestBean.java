package bg.fmi.master.thesis.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

@ManagedBean(name = "checkboxTestBean")
@ViewScoped
public class CheckboxTestBean  implements Serializable {

public List<Boolean> list = new ArrayList<Boolean>();

public CheckboxTestBean() {
    for (int i = 0; i < 5; i++) {
        list.add(Boolean.FALSE);
    }
}

public void actionListener(ActionEvent evt) {
    System.out.println("*** dumping whole form");
    System.out.println("*** list = " + list);
}

public List<Boolean> getList() {
    return list;
}

public void setList(List<Boolean> list) {
    this.list = list;
}
}