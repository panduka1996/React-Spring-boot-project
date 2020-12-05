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
import ie.ucd.rawana.workerapi.services.ShipperService;

@RestController
@RequestMapping("/Shipper")
@CrossOrigin(origins="http://localhost:3000")	
public class ShipperController {

	@Autowired
	private ShipperService shipperService;
	
	@GetMapping("/getShipperOrders")
    public Set<OrderDisplayDto> getShipperOrders() {										
		
		Set<OrderDisplayDto> ordderDto = new HashSet<>();
		
		List<OrderStatus> orders = shipperService.getAllShipperOrders();
			
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
	
	
	@GetMapping("/updateShipperOrder/{OrderId}/{ShipperId}")
    public boolean updatePickerOrder(@PathVariable(value = "OrderId") Long OrdeId,@PathVariable(value = "ShipperId") String ShipperId) {
		
		boolean feedBack = false;
		
		feedBack = shipperService.updateShipperOrder(OrdeId);
		
		if(feedBack) {

			shipperService.updateEmployeeOrder(OrdeId,ShipperId);
		}
		
		return feedBack;
		
	}
	
	
	@GetMapping("/updateShipperOrderToComplete/{OrderId}")
    public boolean updateShipperOrderToComplete(@PathVariable(value = "OrderId") Long OrdeId) {
		
		boolean feedBack = false;

		
		if(shipperService.updateShipperOrderToComplete(OrdeId)) {
			
			feedBack = true;
		}
		
		return feedBack;
		
	}
	
	
	@GetMapping("/getRunningShipperOrders/{ShipperId}")
    public List<RemainEmployeeOrders> getRunningShipperOrders(@PathVariable(value = "ShipperId") String ShipperId) {
		
		List<RemainEmployeeOrders> orderIds = new ArrayList<RemainEmployeeOrders>();
		
		List<String> orderEmps = shipperService.getShipperOrders(ShipperId);
		
		for(String ordId : orderEmps) {
				
			RemainEmployeeOrders reaminOrders = new RemainEmployeeOrders();
			
			reaminOrders.setOrderId(ordId);
			orderIds.add(reaminOrders);
		}
		
		return orderIds;
		
	}
	
	
	
}
