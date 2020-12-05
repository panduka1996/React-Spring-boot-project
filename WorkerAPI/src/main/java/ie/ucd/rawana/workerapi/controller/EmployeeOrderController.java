package ie.ucd.rawana.workerapi.controller;

import ie.ucd.rawana.workerapi.DTO.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/EmployeeOrder")
@CrossOrigin(origins="http://localhost:3000")
public class EmployeeOrderController {
	
    private RestTemplate restTamplate = new RestTemplate();
	
	private String baseUrl = "http://localhost:8083/";

	private String baseUrl1 = "http://localhost:8081/";

	private int step = 0;

	@GetMapping("/orderEmployeeDetails/{orderId}")
    public EmployeeOrderDto getEmployeeOrderDetails(@PathVariable(value = "orderId") Long OrdeId) {
		
		EmployeeOrderDto ordDto = new EmployeeOrderDto();
		
        String url = baseUrl + "orders/" + OrdeId;
		
		ResponseEntity<OrderDto> response =
				restTamplate.getForEntity(url,OrderDto.class);
		
		OrderDto orderdetails = response.getBody();
		
		ordDto.setOrderId(orderdetails.getOrderId());
		ordDto.setCustomerId(orderdetails.getCustomerId());
		ordDto.setCumtomerNumber(orderdetails.getCumtomerNumber());
		ordDto.setLocation(orderdetails.getLocation());
		
		for(ItemDto idto : orderdetails.getCustomerOrder()) {
			
			EmployeeItemDto empItem = new EmployeeItemDto();
			
			empItem.setItemId(idto.getItemId());
			empItem.setCount(idto.getCount());
			empItem.setChecked("unchecked");

			String url1 = baseUrl1 + "getItemLocation/" + idto.getItemId();

			ResponseEntity<String> response1 =
					restTamplate.getForEntity(url1,String.class);

			String location = response1.getBody();

			empItem.setLocation(location);
			
			ordDto.getCustomerOrder().add(empItem);
		}

		return ordDto;
		
	}

	@PutMapping("/step")
	public void getStep(@RequestBody StepDto model){

		step = model.getStep();
	}

	@GetMapping("/step")
	public StepDto getStep(){

		StepDto stepdto = new StepDto();

		stepdto.setStep(step);

		return stepdto;

	}
}
