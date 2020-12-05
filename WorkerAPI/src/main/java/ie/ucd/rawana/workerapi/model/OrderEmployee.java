package ie.ucd.rawana.workerapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OrderEmployee")
public class OrderEmployee {

	
	@Id
	private long orderId;
	
	private String pickingEmployee;
	
	private String packingEmployee;
	
	private String shippingEmployee;
	

	public OrderEmployee() {
		super();
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public String getPickingEmployee() {
		return pickingEmployee;
	}

	public void setPickingEmployee(String pickingEmployee) {
		this.pickingEmployee = pickingEmployee;
	}

	public String getPackingEmployee() {
		return packingEmployee;
	}

	public void setPackingEmployee(String packingEmployee) {
		this.packingEmployee = packingEmployee;
	}

	public String getShippingEmployee() {
		return shippingEmployee;
	}

	public void setShippingEmployee(String shippingEmployee) {
		this.shippingEmployee = shippingEmployee;
	}
	
	
}
