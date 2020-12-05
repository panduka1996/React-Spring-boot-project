package ie.ucd.rawana.workerapi.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.ucd.rawana.workerapi.DTO.OrderDisplayDto;
import ie.ucd.rawana.workerapi.DTO.RemainEmployeeOrders;
import ie.ucd.rawana.workerapi.model.OrderStatus;
import ie.ucd.rawana.workerapi.services.PackerService;

@RestController
@RequestMapping("/Packer")
@CrossOrigin(origins="http://localhost:3000")
public class PackerController {

	
	@Autowired
	private PackerService packerService;
	
	@GetMapping("/getPackerOrders")
    public Set<OrderDisplayDto> getPackerOrders() {
		
		Set<OrderDisplayDto> ordderDto = new HashSet<>();
		
		List<OrderStatus> orders = packerService.getAllPackerOrders();
		
		for(OrderStatus ord : orders) {
			
			OrderDisplayDto ordDis = new OrderDisplayDto();
			
			ordDis.setOrderId(ord.getOrderId());								
			ordDis.setPackingState(ord.getPackingState());
			ordDis.setPickingState(ord.getPickingState());
			ordDis.setShippingState(ord.getShippingState());
			
			ordderDto.add(ordDis);
			
		}
		
		return ordderDto;
		
	}
	
	
	@GetMapping("/updatePackerOrder/{OrderId}/{PackerId}")
    public boolean updatePickerOrder(@PathVariable(value = "OrderId") Long OrdeId,@PathVariable(value = "PackerId") String PackerId) {
		
		boolean feedBack = false;
		
		feedBack = packerService.updatePackerOrder(OrdeId);
		
		if(feedBack) {
			
			packerService.updateEmployeeOrder(OrdeId,PackerId);
		}
		
		return feedBack;
		
	}
	
	
	@GetMapping("/updatePackerOrderToComplete/{OrderId}")
    public boolean updatePickerOrderToComplete(@PathVariable(value = "OrderId") Long OrdeId) {
		
		boolean feedBack = false;

		
		if(packerService.updatePackerOrderToComplete(OrdeId)) {
			
			feedBack = true;
		}
		
		return feedBack;
		
	}
	
	@GetMapping("/getRunningPackerOrders/{packerId}")
    public List<RemainEmployeeOrders> getRunningPickerOrders(@PathVariable(value = "packerId") String packerId) {
		
		List<RemainEmployeeOrders> orderIds = new ArrayList<RemainEmployeeOrders>();
	
		List<String> orderEmps = packerService.getPackerOrders(packerId);
		
		for(String ordId : orderEmps) {
			
			RemainEmployeeOrders reaminOrders = new RemainEmployeeOrders();
			
			reaminOrders.setOrderId(ordId);
			orderIds.add(reaminOrders);
		}
		
		return orderIds;
		
	}
	
	
	
}
