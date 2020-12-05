package ie.ucd.rawana.workerapi.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import ie.ucd.rawana.workerapi.DTO.*;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import ie.ucd.rawana.workerapi.model.OrderEmployee;
import ie.ucd.rawana.workerapi.model.OrderStatus;
import ie.ucd.rawana.workerapi.services.PickerService;

@RestController
@RequestMapping("/Picker")
@CrossOrigin(origins="http://localhost:3000")
public class PickerController {
	
	@Autowired
	private PickerService pickerService;
	
	private RestTemplate restTamplate = new RestTemplate();
	
	private String baseUrl = "http://localhost:8081/";

	private String baseUrl1 = "http://localhost:8083/";

	private SimuOrder order = new SimuOrder();

	private List<WorkerAction> actions = new ArrayList<WorkerAction>();

	private String pickername = "";
	
	@GetMapping("/getPickerOrders")
    public Set<PickerOrderDisplayDto> getPickerOrders() {
		
		Set<PickerOrderDisplayDto> ordderDto = new HashSet<>();
		
		List<OrderStatus> orders = pickerService.getAllPickerOrders();
		
		for(OrderStatus ord : orders) {

			PickerOrderDisplayDto ordDis = new PickerOrderDisplayDto();

			String itemWeight = orderItems(ord.getOrderId());

			ordDis.setOrderId(ord.getOrderId());
			ordDis.setMaxWeight(itemWeight);
			ordDis.setPackingState(ord.getPackingState());
			ordDis.setPickingState(ord.getPickingState());
			ordDis.setShippingState(ord.getShippingState());
			
			ordderDto.add(ordDis);
			
		}
		
		return ordderDto;
		
	}
	
	
	@GetMapping("/updatePickerOrder/{OrderId}/{PickerId}")
    public boolean updatePickerOrder(@PathVariable(value = "OrderId") Long OrdeId,@PathVariable(value = "PickerId") String PickerId) {
		
		boolean feedBack = false;
		
		boolean feedback1 = false;

		List<String> orderEmps = pickerService.getPickerOrders(PickerId);

		if(orderEmps.size() == 0){

			feedback1 = pickerService.updatePickerOrder(OrdeId);

			OrderEmployee orderEmp = new OrderEmployee();

			orderEmp.setOrderId(OrdeId);
			orderEmp.setPickingEmployee(PickerId);
			orderEmp.setPackingEmployee("");
			orderEmp.setShippingEmployee("");

			if(feedback1) {

				feedBack = pickerService.updateEmployeeOrder(orderEmp);
			}

		}
			
		return feedBack;
		
	}
	
	
	@GetMapping("/updatePickerOrderToComplete/{OrderId}")
    public boolean updatePickerOrderToComplete(@PathVariable(value = "OrderId") Long OrdeId) throws IOException {
		
		boolean feedBack = false;

		
		if(pickerService.updatePickerOrderToComplete(OrdeId)) {
			
			feedBack = true;

			try{

				File directory = new File("..//paper-dashboard//src//views//images//diagrams//");
				File[] files = directory.listFiles();
				for (File file : files){
					if (!file.delete()){
						System.out.println("Failed to delete "+file);
					}else{
						System.out.println("File delete : "+file);
					}
				}


                String url = baseUrl + "configuration";

                ResetConfig config = new ResetConfig();
                Set<WorkerDto> workerset = new HashSet<WorkerDto>();
                Set<ItemDto> itemset = new HashSet<ItemDto>();
                String[] packagingAreas = {"a1.3","a2.3"};

                config.setAisles("2");
                config.setSections("2");
                config.setShelves("1");
                config.setPackingAreas(packagingAreas);
                config.setWorkers(workerset);
                config.setItems(itemset);

                restTamplate.put(url,config);

			}
			catch (Exception e){

			}


		}
		
		return feedBack;
		
	}
	
	
	@GetMapping("/getRunningPickerOrders/{pickerId}")
    public List<RemainEmployeeOrders> getRunningPickerOrders(@PathVariable(value = "pickerId") String pickerId) {
		
		List<RemainEmployeeOrders> orderIds = new ArrayList<RemainEmployeeOrders>();
		
		List<String> orderEmps = pickerService.getPickerOrders(pickerId);
		
		for(String ordId : orderEmps) {

			RemainEmployeeOrders reaminOrders = new RemainEmployeeOrders();
			
			reaminOrders.setOrderId(ordId);
			orderIds.add(reaminOrders);
		}
		
		return orderIds;
		
	}


	@GetMapping("/getPickerAccuracyDetails")
	public Set<PickerAccuracyDto> getPickerAccuracyDetails() {


		Set<PickerAccuracyDto> pickerAcc = new HashSet<>();

		String url = baseUrl + "getPickers";

		ResponseEntity<String[]> response =
				restTamplate.getForEntity(url,String[].class);

		String[] workerIds = response.getBody();

		for(int i=0;i<workerIds.length;i++) {

			PickerAccuracyDto pickA =  new PickerAccuracyDto();

			String orderCount = pickerService.getCompletedOrderCount(workerIds[i]);

			String orderRunningCount = pickerService.getRunningOrderCount(workerIds[i]);

			if(orderCount == null) {

				orderCount = "0";
			}

			if(orderRunningCount == null) {

				orderRunningCount = "0";
			}

			pickA.setPickerId(workerIds[i]);
			pickA.setCompletedOrders(orderCount);
			pickA.setRunningOrders(orderRunningCount);

			pickerAcc.add(pickA);
		}


		return pickerAcc;

	}
	
	
	@GetMapping("/getRightWorker/{orderId}")
    public String getRightWorker(@PathVariable(value = "orderId") long orderId) {

		String url = baseUrl + "getPickers";

		ResponseEntity<String[]> response =
				restTamplate.getForEntity(url,String[].class);

		String[] pickerWorkerIds = response.getBody();

		List<String> allowedWorkerIds = new ArrayList<String>();

		String itemWeight = orderItems(orderId);

		for(int i=0;i<pickerWorkerIds.length;i++){

			String url1 = baseUrl + "getWorkerWeight/" + pickerWorkerIds[i];

			ResponseEntity<String> response1 =
					restTamplate.getForEntity(url1,String.class);

			String workerWeight = response1.getBody();

			if(Float.parseFloat(workerWeight) > Float.parseFloat(itemWeight)){
				allowedWorkerIds.add(pickerWorkerIds[i]);
			}

		}

		String[] workerIds = new String[allowedWorkerIds.size()];

		for(int i=0;i<allowedWorkerIds.size();i++){

			workerIds[i] = allowedWorkerIds.get(i);
		}

		String rightWorkerId = "";

		int min = 100;

		for(int i=0;i<workerIds.length;i++) {

			String orderCount = pickerService.getCompletedOrderCount(workerIds[i]);

			String orderRunningCount = pickerService.getRunningOrderCount(workerIds[i]);


			if(orderCount == null) {

				orderCount = "0";
			}

			if(orderRunningCount == null) {

				orderRunningCount = "0";
			}


			if(Integer.parseInt(orderRunningCount) == 0) {

				if(Integer.parseInt(orderCount) <= min) {

					min = Integer.parseInt(orderCount);

					rightWorkerId = workerIds[i];
				}

			}

		}


		if(rightWorkerId == "") {

			rightWorkerId = "Not Available";

		}

		return rightWorkerId;
		
	}


	@GetMapping("/orderItems/{orderId}")
	public String orderItems(@PathVariable(value = "orderId") long orderId){

		String url = baseUrl1 + "getOrderItems/" + orderId;

		ResponseEntity<ItemIdOrderDto[]> response =
				restTamplate.getForEntity(url, ItemIdOrderDto[].class);

		ItemIdOrderDto[] itemIds = response.getBody();

		List<String> weightList = new ArrayList<String>();
		for(ItemIdOrderDto itemid : itemIds){

			String url1 = baseUrl + "getItemWeight/" + itemid.getItemId();

			ResponseEntity<String> response1 =
					restTamplate.getForEntity(url1, String.class);

			String weight = response1.getBody();

			weightList.add(weight);

		}

		String maxWeight = Collections.max(weightList,null);

		return maxWeight;
	}

	@GetMapping("/getAllocatedOrder/{pickerId}")
	public long getAllocatedOrder(@PathVariable(value = "pickerId") String pickerId){

		List<String> orderEmps = pickerService.getPickerOrders(pickerId);

		if(orderEmps.size() != 0){

		return Long.parseLong(orderEmps.get(0));
		}
		
		return 0;
	}

	@PostMapping("/mappingOrder/{picker}")
	public void mappingOrder(@RequestBody SimuOrder model,@PathVariable(value = "picker") String picker) throws IOException {

		order = model;
		pickername = picker;

		Set<SimuItem> Simuitems = model.getItems();

		List<String> itemIds = new ArrayList<String>();

		for(SimuItem items : Simuitems){

			itemIds.add(items.getItemId());

		}

		int minLength = 100;
		String minItem = "";
        String itemSou = "a1.0";

		for(int i=0;i<itemIds.size();i++){

			String urlmin = baseUrl + "getItemLocation/" + itemIds.get(i);

			ResponseEntity<String> responsemin =
					restTamplate.getForEntity(urlmin,String.class);

			String locationmin = responsemin.getBody();

			String[] splitLocationmin = locationmin.split("[/]");

			String url = baseUrl + "getCloseItem/" + itemSou + "/" + splitLocationmin[0];

			ResponseEntity<String> response =
					restTamplate.getForEntity(url,String.class);

			String pathLength = response.getBody();
			int length = pathLength.length();

			if(length <= minLength){

				minItem = itemIds.get(i);
				minLength = length;
			}

		}

		String urlmin = baseUrl + "getItemLocation/" + minItem;

		ResponseEntity<String> responsemin =
				restTamplate.getForEntity(urlmin,String.class);

		String locationmin = responsemin.getBody();

		String[] splitLocationmin = locationmin.split("[/]");

		WorkerAction actionmin = new WorkerAction();
		actionmin.setType("move");
		actionmin.setArguments(splitLocationmin[0]);

		actions.add(actionmin);

		WorkerAction action1min = new WorkerAction();
		action1min.setType("pick");
		action1min.setArguments(splitLocationmin[1]);

		actions.add(action1min);

		WorkerAction action2min = new WorkerAction();
		action2min.setType("drop");
		action2min.setArguments(splitLocationmin[0]);

		actions.add(action2min);

		itemIds.remove(minItem);

		String lastItemLocation = splitLocationmin[0];

		if(itemIds.size() != 0){

			for(int i=0;i<itemIds.size();i++){

				String url = baseUrl + "getItemLocation/" + itemIds.get(i);

				ResponseEntity<String> response =
						restTamplate.getForEntity(url,String.class);

				String location = response.getBody();

				String[] splitLocation = location.split("[/]");

				WorkerAction action = new WorkerAction();
				action.setType("move");
				action.setArguments(splitLocation[0]);

				actions.add(action);

				WorkerAction action1 = new WorkerAction();
				action1.setType("pick");
				action1.setArguments(splitLocation[1]);

				actions.add(action1);

				WorkerAction action2 = new WorkerAction();
				action2.setType("drop");
				action2.setArguments(splitLocation[0]);

				actions.add(action2);

				lastItemLocation = splitLocation[0];
			}

		}


		int packStation1 = 0;
		int packStation2 = 0;

		String url = baseUrl + "getCloseItem/" + lastItemLocation + "/a1.5";

		ResponseEntity<String> response =
				restTamplate.getForEntity(url,String.class);

		String pathLength = response.getBody();
		packStation1 = pathLength.length();

		String url1 = baseUrl + "getCloseItem/" + lastItemLocation + "/a2.5";

		ResponseEntity<String> response1 =
				restTamplate.getForEntity(url1,String.class);

		String pathLength1 = response1.getBody();
		packStation2 = pathLength1.length();

		String packingLocation = "";
		
		if(packStation1 <= packStation2){

            packingLocation = "a1.5";
        }
		else{
            packingLocation = "a2.5";
        }


		WorkerAction action3 = new WorkerAction();
		action3.setType("move");
		action3.setArguments(packingLocation);

		actions.add(action3);

		WorkerAction action4 = new WorkerAction();
		action4.setType("pack");
		action4.setArguments(Long.toString(order.getOrderId()));

		actions.add(action4);

		File file = new File("..//paper-dashboard//src//views//images//diagrams//");
		file.mkdir();

		AcceptAction action = new AcceptAction();

		action.setType("INIT");
		action.setArguments("INIT");
		action.setSuccess(true);
		action.setStep(0);

		actionCompleted(action);

	}

	@PostMapping("/actionCompleted")
	public void actionCompleted(@RequestBody AcceptAction model){

		String url = baseUrl + "workers/" + pickername + "/nextAction";

		for(int i=0;i<actions.size();i++){

			restTamplate.put(url,actions.get(i));
		}

		actions.clear();
        order = new SimuOrder();
        pickername = "";
	}


}
