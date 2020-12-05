package ie.ucd.rawana.workerapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OrderStatus")
public class OrderStatus {				

	@Id
	private long orderId;
	
	private String pickingState;
	
	private String packingState;
	
	private String shippingState;
	

	public OrderStatus() {
		
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getPickingState() {
		return pickingState;
	}

	public void setPickingState(String pickingState) {
		this.pickingState = pickingState;
	}

	public String getPackingState() {
		return packingState;
	}

	public void setPackingState(String packingState) {
		this.packingState = packingState;
	}

	public String getShippingState() {
		return shippingState;
	}

	public void setShippingState(String shippingState) {
		this.shippingState = shippingState;
	}
	
	
	
	
}
