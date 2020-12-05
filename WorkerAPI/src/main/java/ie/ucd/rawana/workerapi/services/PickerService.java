package ie.ucd.rawana.workerapi.services;

import java.util.List;

import ie.ucd.rawana.workerapi.model.OrderEmployee;
import ie.ucd.rawana.workerapi.model.OrderStatus;

public interface PickerService {
	
	public List<OrderStatus> getAllPickerOrders();
	
	public boolean updatePickerOrder(long orderId);
	
	public boolean updateEmployeeOrder(OrderEmployee orderEmp);
	
	public boolean updatePickerOrderToComplete(long orderId);
	
	public List<String> getPickerOrders(String pickerId);
	
	public String getCompletedOrderCount(String pickerId);
	
	public String getRunningOrderCount(String pickerId);

}
