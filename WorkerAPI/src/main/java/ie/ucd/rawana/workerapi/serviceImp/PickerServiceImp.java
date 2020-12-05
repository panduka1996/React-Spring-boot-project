package ie.ucd.rawana.workerapi.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.ucd.rawana.workerapi.model.OrderEmployee;
import ie.ucd.rawana.workerapi.model.OrderStatus;
import ie.ucd.rawana.workerapi.repository.OrderEmployeeRepository;
import ie.ucd.rawana.workerapi.repository.OrderStatusRepository;
import ie.ucd.rawana.workerapi.services.PickerService;

@Service
public class PickerServiceImp implements PickerService{

	@Autowired
	private OrderStatusRepository orderStatusRepository;
	
	@Autowired
	private OrderEmployeeRepository orderEmployeeRepository;

	@Override
	public List<OrderStatus> getAllPickerOrders() {
		
		return orderStatusRepository.getAllPickerOrders();
	}

	@Override
	public boolean updatePickerOrder(long orderId) {
		

		boolean feedback = false;
		
		try {
			
			orderStatusRepository.updatePickerOrder(orderId);
			
			feedback = true;
			
		}
		catch(Exception e) {
			
			feedback = false;
		}
		
		return feedback;
	}
	
	
	@Override
	public boolean updateEmployeeOrder(OrderEmployee orderEmp) {
		

		boolean feedback = false;
		
		try {
			
			orderEmployeeRepository.save(orderEmp);
			
			feedback = true;
			
		}
		catch(Exception e) {
			
			feedback = false;
		}
		
		return feedback;
	}


	@Override
	public boolean updatePickerOrderToComplete(long orderId) {
		

		boolean feedback = false;
		
		try {
			
			orderStatusRepository.updatePickerOrderToComplete(orderId);
			
			feedback = true;
			
		}
		catch(Exception e) {
			
			feedback = false;
		}
		
		return feedback;
	}

	@Override
	public List<String> getPickerOrders(String pickerId) {
		
		return orderStatusRepository.getPickerOrders(pickerId);
	}

	@Override
	public String getCompletedOrderCount(String pickerId) {
		
		return orderStatusRepository.getCompletedOrderCount(pickerId);
	}

	@Override
	public String getRunningOrderCount(String pickerId) {
		
		return orderStatusRepository.getRunningOrderCount(pickerId);
	}



	
}
