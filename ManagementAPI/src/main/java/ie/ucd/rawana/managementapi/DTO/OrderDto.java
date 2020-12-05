package ie.ucd.rawana.managementapi.DTO;

import java.util.HashSet;
import java.util.Set;


public class OrderDto {
	
	private long orderId;
	
	private String customerId;
	
	private String cumtomerNumber;
	
	private String location;
	
    private String pickingState;
	
	private String packingState;
	
	private String shippingState;
	
	private Set<ItemDto> customerOrder = new HashSet<>();
	

	public OrderDto() {
		
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCumtomerNumber() {
		return cumtomerNumber;
	}

	public void setCumtomerNumber(String cumtomerNumber) {
		this.cumtomerNumber = cumtomerNumber;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Set<ItemDto> getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(Set<ItemDto> customerOrder) {
		this.customerOrder = customerOrder;
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
