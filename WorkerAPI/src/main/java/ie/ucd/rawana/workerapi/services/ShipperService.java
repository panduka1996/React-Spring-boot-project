package ie.ucd.rawana.workerapi.services;

import java.util.List;

import ie.ucd.rawana.workerapi.model.OrderStatus;

public interface ShipperService {
	
	public List<OrderStatus> getAllShipperOrders();
	
	public boolean updateShipperOrder(long orderId);
	
	public void updateEmployeeOrder(long orderId,String shipperId);
	
	public boolean updateShipperOrderToComplete(long orderId);
	
	public List<String> getShipperOrders(String ShipperId);

}
