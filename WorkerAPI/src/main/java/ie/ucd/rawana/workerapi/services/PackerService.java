package ie.ucd.rawana.workerapi.services;

import java.util.List;

import ie.ucd.rawana.workerapi.model.OrderStatus;

public interface PackerService {

	public List<OrderStatus> getAllPackerOrders();
	
	public boolean updatePackerOrder(long orderId);
	
	public void updateEmployeeOrder(long orderId,String packerId);
	
	public boolean updatePackerOrderToComplete(long orderId);
	
	public List<String> getPackerOrders(String packerId);
}
