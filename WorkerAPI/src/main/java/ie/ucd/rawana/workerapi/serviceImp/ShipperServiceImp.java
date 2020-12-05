package ie.ucd.rawana.workerapi.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.ucd.rawana.workerapi.model.OrderStatus;
import ie.ucd.rawana.workerapi.repository.OrderEmployeeRepository;
import ie.ucd.rawana.workerapi.repository.OrderStatusRepository;
import ie.ucd.rawana.workerapi.services.ShipperService;

@Service
public class ShipperServiceImp implements ShipperService{
	
	@Autowired
	private OrderStatusRepository orderStatusRepository;
	
	@Autowired
	private OrderEmployeeRepository orderEmployeeRepository;

	@Override
	public List<OrderStatus> getAllShipperOrders() {
		
		return orderStatusRepository.getAllShipperOrders();
	}

	@Override
	public boolean updateShipperOrder(long orderId) {
		

		boolean feedback = false;
		
		try {
			
			orderStatusRepository.updateShipperOrder(orderId);
			
			feedback = true;
			
		}
		catch(Exception e) {
			
			feedback = false;
		}
		
		return feedback;
	}

	
	@Override
	public void updateEmployeeOrder(long orderId, String shipperId) {
		
		orderEmployeeRepository.updateShippingEmployeeOrder(orderId,shipperId);
		
	}
	
	@Override
	public boolean updateShipperOrderToComplete(long orderId) {
		

		boolean feedback = false;
		
		try {
			
			orderStatusRepository.updateShipperOrderToComplete(orderId);
			
			feedback = true;
			
		}
		catch(Exception e) {
			
			feedback = false;
		}
		
		return feedback;
	}

	@Override
	public List<String> getShipperOrders(String ShipperId) {
		
		return orderStatusRepository.getShipperOrders(ShipperId);
	}



}
