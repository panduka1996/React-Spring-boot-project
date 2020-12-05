package ie.ucd.rawana.workerapi.DTO;

import java.util.HashSet;
import java.util.Set;

public class EmployeeOrderDto {
	

    private long orderId;
	
	private String customerId;
	
	private String cumtomerNumber;
	
	private String location; 
	
	private Set<EmployeeItemDto> customerOrder = new HashSet<>();

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
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

	public Set<EmployeeItemDto> getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(Set<EmployeeItemDto> customerOrder) {
		this.customerOrder = customerOrder;
	}
	
	
}
