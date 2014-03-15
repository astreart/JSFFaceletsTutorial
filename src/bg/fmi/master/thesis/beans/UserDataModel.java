package bg.fmi.master.thesis.beans;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import bg.fmi.master.thesis.model.TUser;


public class UserDataModel extends ListDataModel<TUser> implements SelectableDataModel<TUser> {
	
	
	public UserDataModel() {  
    }  
  
    public UserDataModel(List<TUser> data) {  
        super(data);  
    }  
	

	@Override
	public Object getRowKey(TUser tUser) {
		// TODO Auto-generated method stub
		return tUser.getId();
	}

	@Override
	public TUser getRowData(String rowKey) {
		//In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
        
        List<TUser> tUsers = (List<TUser>) getWrappedData();  
          
        for(TUser tUser : tUsers) {  
            if(tUser.getId()==(Integer.valueOf(rowKey)))  
                return tUser;  
        }  
          
        return null;
	}

}
