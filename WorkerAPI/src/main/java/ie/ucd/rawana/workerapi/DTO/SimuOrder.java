package ie.ucd.rawana.workerapi.DTO;

import java.util.HashSet;
import java.util.Set;

public class SimuOrder {

	private long orderId;
	
	private Set<SimuItem> Items = new HashSet<>();

	public SimuOrder() {
		
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public Set<SimuItem> getItems() {
		return Items;
	}

	public void setItems(Set<SimuItem> items) {
		Items = items;
	}


	
	
}
