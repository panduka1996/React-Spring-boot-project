package ie.ucd.rawana.managementapi.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ie.ucd.rawana.managementapi.DTO.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/Manager")
@CrossOrigin(origins="http://localhost:3000")
public class ManageController {


	private RestTemplate restTamplate = new RestTemplate();
	
	private String baseUrl = "http://localhost:8083/";
	
	private String baseUrl1 = "http://localhost:8084/";
	
	private String baseUrl2 = "http://localhost:8081/";

	private String baseUrl3 = "http://localhost:9000/";

	private int step = 0;
	
	@GetMapping("/orders")
    public OrderDisplayDto[] getDailyOrders() {
		
		String url = baseUrl + "orders";
		
		ResponseEntity<OrderDisplayDto[]> response =
				restTamplate.getForEntity(url,OrderDisplayDto[].class);
		
		OrderDisplayDto[] orders = response.getBody();
		
		
		return orders;
		
	}
	
	
	@GetMapping("/orders/{orderId}")
    public OrderDto getOrderDetails(@PathVariable(value = "orderId") Long OrdeId) {
			
		String url = baseUrl + "orders/" + OrdeId;
		
		ResponseEntity<OrderDto> response =
				restTamplate.getForEntity(url,OrderDto.class);
		
		OrderDto orders = response.getBody();
		
		return orders;
		
		
	}
	
	
	@GetMapping("/getPickerAccuracyDetails")
    public PickerAccuracyDto[] getPickerAccuracyDetails() {
			
		String url = baseUrl1 + "Picker/getPickerAccuracyDetails";
		
		ResponseEntity<PickerAccuracyDto[]> response =
				restTamplate.getForEntity(url,PickerAccuracyDto[].class);
		
		PickerAccuracyDto[] pickerAcc = response.getBody();
		
		return pickerAcc;
		
		
	}
	
	
	@GetMapping("/getNeworders")
    public List<NewOrders> getNeworders() {
			
		String url = baseUrl + "getNewOrders";
		
		List<NewOrders> neworders = new ArrayList<NewOrders>();
		
		ResponseEntity<String[]> response =
				restTamplate.getForEntity(url,String[].class);
		
		String[] orders = response.getBody();
		
		
		for(String order : orders) {
			
			NewOrders neword = new NewOrders();
			
			neword.setOrderId(order);
			
			neworders.add(neword);
			
		}
		
		return neworders;
		
		
	}
	
	
	@GetMapping("/getWorkers")
    public List<PickerName> getWorkers() {
			
		String url = baseUrl2 + "Workers";
		
		List<PickerName> pickernames = new ArrayList<PickerName>();
		
		ResponseEntity<String[]> response =
				restTamplate.getForEntity(url,String[].class);
		
		String[] pickers = response.getBody();
		
		
		for(String order : pickers) {
			
			PickerName pickername = new PickerName();
			
			pickername.setPickerName(order);
			
			pickernames.add(pickername);
			
		}
		
		return pickernames;
		
		
	}
	
	
	@GetMapping("/updatePickerOrder/{OrderId}/{PickerId}")
    public boolean updatePickerOrder(@PathVariable(value = "OrderId") Long OrdeId,@PathVariable(value = "PickerId") String PickerId) {
		
		boolean feedBack = false;
		
		String url = baseUrl1 + "Picker/updatePickerOrder/" + OrdeId + "/" + PickerId;
		
		ResponseEntity<Boolean> response =
				restTamplate.getForEntity(url,Boolean.class);
		
		feedBack = response.getBody();
			
		return feedBack;
		
	}										
	
	
	@GetMapping("/getRightWorker/{orderId}")
    public String getRightWorker(@PathVariable(value = "orderId") long orderId) {
			
		String url = baseUrl1 + "Picker/getRightWorker/" + orderId;
		
		ResponseEntity<String> response =
				restTamplate.getForEntity(url,String.class);
		
		String pickerId = response.getBody();
		
		return pickerId;
		
		
	}

	@GetMapping("/registerToClock")
	public boolean getRegister(){

		boolean feedBack = false;

		try{

			RestTemplate restTamplate = new RestTemplate();

			String baseUrl = "http://localhost:9000/registry";

			String url = baseUrl;

			ClockRegistry clock = new ClockRegistry();

			clock.setName("OrderGenerateAPI");
			clock.setUri("http://localhost:8083/step");

			ResponseEntity<ClockResponse> response =
					restTamplate.postForEntity(url,clock,ClockResponse.class);

			ClockResponse status = response.getBody();

			ClockRegistry clock1 = new ClockRegistry();

			clock1.setName("SimulatorAPI");
			clock1.setUri("http://localhost:8081/step");

			ResponseEntity<ClockResponse> response1 =
					restTamplate.postForEntity(url,clock1,ClockResponse.class);

			ClockResponse status1 = response.getBody();

			ClockRegistry clock2 = new ClockRegistry();

			clock2.setName("ManagementAPI");
			clock2.setUri("http://localhost:8085/Manager/step");

			ResponseEntity<ClockResponse> response2 =
					restTamplate.postForEntity(url,clock2,ClockResponse.class);

			ClockResponse status2 = response2.getBody();

			ClockRegistry clock3 = new ClockRegistry();

			clock3.setName("WorkerAPI");
			clock3.setUri("http://localhost:8084/EmployeeOrder/step");

			ResponseEntity<ClockResponse> response3 =
					restTamplate.postForEntity(url,clock3,ClockResponse.class);

			ClockResponse status3 = response3.getBody();

			feedBack = true;

		}
		catch (Exception e){

			feedBack = false;
		}

		return feedBack;


	}
	
    @GetMapping("/autoAllocateOrder")
	public void autoAllocateOrder(){

		String url = baseUrl + "getTopOrder";

		ResponseEntity<Long> response =
				restTamplate.getForEntity(url,Long.class);

		long orderId = response.getBody();

		if(orderId != 0){

			String pickerId = getRightWorker(orderId);

			if(!pickerId.equals("Not Available")){

				updatePickerOrder(orderId,pickerId);

			}
			else{

				System.out.println("not allocated");
			}

		}


	}

	@PutMapping("/step")
	public void getStep(@RequestBody StepDto model){

		step = model.getStep();

		autoAllocateOrder();

	}

	@GetMapping("/step")
	public int getStep(){

		return step;

	}

	@GetMapping("/registry")
	public ServiceRegistrySet registry(){

		String url = baseUrl3 + "registry";

		ServiceRegistrySet newResgiter = new ServiceRegistrySet();

		ResponseEntity<ServiceRegistrySet> response =
				restTamplate.getForEntity(url,ServiceRegistrySet.class);

		ServiceRegistrySet services = response.getBody();

		for(ServiceRegistry serv : services.getRegistrations()){

			ServiceRegistry newservice = new ServiceRegistry();
			newservice.setName(serv.getName());
			newservice.setUri(serv.getUri());

			if(serv.getFailed().equals("false")){
				newservice.setFailed("Connected");
			}
			else{
				newservice.setFailed("Disconnected");
			}

			newResgiter.getRegistrations().add(newservice);
		}

		return newResgiter;
	}
	
}
