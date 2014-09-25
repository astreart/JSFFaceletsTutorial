package bg.fmi.master.thesis.utils;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import bg.fmi.master.thesis.models.TEventType;
import bg.fmi.master.thesis.models.TUser;

public class EventTypeDataUtil extends ListDataModel<TEventType> implements
		SelectableDataModel<TEventType>, Serializable {

	private static final long serialVersionUID = 1L;

	public EventTypeDataUtil() {
	}

	public EventTypeDataUtil(List<TEventType> data) {
		super(data);
	}

	@Override
	public Object getRowKey(TEventType tEventType) {
		return tEventType.getId();
	}

	@Override
	public TEventType getRowData(String rowKey) {
		List<TEventType> tEventTypes = (List<TEventType>) getWrappedData();

		for (TEventType tEventType : tEventTypes) {
			if (tEventType.getId() == (Long.valueOf(rowKey)))
				return tEventType;
		}

		return null;
	}

	// remove Element
	public List<TEventType> removeEventType(List<TEventType> eventTypes,
			TEventType tEventType) {
		for (TEventType eventType : eventTypes) 
			if (eventType == tEventType) {
				eventTypes.remove(tEventType);
				return eventTypes;
			}
			return null;
	}
}
