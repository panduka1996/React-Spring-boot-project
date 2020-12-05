package ie.ucd.rawana.workerapi.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.ucd.rawana.workerapi.model.OrderStatus;
import ie.ucd.rawana.workerapi.repository.OrderEmployeeRepository;
import ie.ucd.rawana.workerapi.repository.OrderStatusRepository;
import ie.ucd.rawana.workerapi.services.PackerService;

@Service
public class PackerServiceImp implements PackerService{

	@Autowired
	private OrderStatusRepository orderStatusRepository;
	
	@Autowired
	private OrderEmployeeRepository orderEmployeeRepository;

	@Override
	public List<OrderStatus> getAllPackerOrders() {
		
		return orderStatusRepository.getAllPackerOrders();
	}

	@Override
	public boolean updatePackerOrder(long orderId) {
		

		boolean feedback = false;
		
		try {
			
			orderStatusRepository.updatePackerOrder(orderId);
			
			feedback = true;
			
		}
		catch(Exception e) {
			
			feedback = false;
		}
		
		return feedback;
		
	}
	
	
	@Override
	public void updateEmployeeOrder(long orderId,String packerId) {
		
		orderEmployeeRepository.updateEmployeeOrder(orderId,packerId);
		
	}
	

	@Override
	public boolean updatePackerOrderToComplete(long orderId) {
		
		boolean feedback = false;
		
		try {
			
			orderStatusRepository.updatePackerOrderToComplete(orderId);
			
			feedback = true;
			
		}
		catch(Exception e) {
			
			feedback = false;
		}
		
		return feedback;
	}

	@Override
	public List<String> getPackerOrders(String packerId) {
		
		return orderStatusRepository.getPackerOrders(packerId);
	}


	
	
}
