package ie.ucd.rawana.ordergenerateapi.controller;

import java.util.*;

import ie.ucd.rawana.ordergenerateapi.DTO.*;
import ie.ucd.rawana.ordergenerateapi.serviceImp.AutoGenerateOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import ie.ucd.rawana.ordergenerateapi.model.Order;
import ie.ucd.rawana.ordergenerateapi.model.OrderStatus;
import ie.ucd.rawana.ordergenerateapi.model.OrderItem;
import ie.ucd.rawana.ordergenerateapi.services.OrderGenerateService;
import ie.ucd.rawana.ordergenerateapi.services.OrderStatusService;


@RestController
@CrossOrigin(origins="http://localhost:3000")
public class MakeOrderController {


    @Autowired
    private OrderGenerateService orderGenerateService;

    @Autowired
    private OrderStatusService orderStatusService;

    private RestTemplate restTamplate = new RestTemplate();

    private String baseUrl = "http://localhost:8081/";

    private int step = 0;

    @PostMapping("/saveOrder")
    public boolean passOrder(@RequestBody OrderDto model) {


        boolean feedBack = false;

        Order order = new Order();

        order.setCustomerId(model.getCustomerId());
        order.setContactNo(model.getCumtomerNumber());
        order.setLocation(model.getLocation());

        for(ItemDto b1 : model.getCustomerOrder()) {

            OrderItem bt = new OrderItem();
            bt.setItemId(b1.getItemId());
            bt.setQuantity(b1.getCount());
            bt.setOrder(order);

            order.getItem().add(bt);

        }

        orderGenerateService.saveOrder(order);

        long orderId = order.getId();

        OrderStatus orderEmp = new OrderStatus();

        orderEmp.setOrderId(orderId);
        orderEmp.setPackingState("0");
        orderEmp.setPickingState("0");
        orderEmp.setShippingState("0");

        if(orderStatusService.saveOrderEmployee(orderEmp)) {


            feedBack = true;

        }

        return feedBack;


    }


    @GetMapping("/orders/{orderId}")
    public OrderDto getOrderDetails(@PathVariable(value = "orderId") Long OrdeId) {


        OrderDto ordDto = new OrderDto();

        Order order = orderGenerateService.findOrderById(OrdeId);

        ordDto.setOrderId(order.getId());
        ordDto.setCustomerId(order.getCustomerId());
        ordDto.setCumtomerNumber(order.getContactNo());
        ordDto.setLocation(order.getLocation());

        OrderStatus orderEmp = orderStatusService.getOrderById(OrdeId);

        ordDto.setPickingState(orderEmp.getPickingState());
        ordDto.setPackingState(orderEmp.getPackingState());
        ordDto.setShippingState(orderEmp.getShippingState());

        for(OrderItem orditem : order.getItem()) {

            ItemDto ido = new ItemDto();

            ido.setItemId(orditem.getItemId());
            ido.setCount(orditem.getQuantity());

            ordDto.getCustomerOrder().add(ido);
        }

        return ordDto;


    }



    @GetMapping("/orders")
    public Set<OrderDisplayDto> getDailyOrders() {

        Set<OrderDisplayDto> ordderDto = new HashSet<>();

        List<OrderStatus> orders = orderStatusService.getAllOrders();

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


    @GetMapping("/getNewOrders")
    public List<String> getNeworders() {

        List<String> orders = orderStatusService.getNeworders();

        return orders;

    }


    @GetMapping("/generateNewOrders")
    public void generateNewOrders() {

        String url = baseUrl + "items";

        ResponseEntity<ItemIdDto[]> response =
                restTamplate.getForEntity(url,ItemIdDto[].class);

        ItemIdDto[] ItemIds = response.getBody();

        Integer[] probitemArray = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,4,4,4,4,4,4,4,4,4,4,4,4,4,5,5,5,5,5,5};

        List<Integer> probitemList = Arrays.asList(probitemArray);

        Collections.shuffle(probitemList);

        int noofitems = probitemList.get(0);

        Integer[] probquanArray = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3,3,3 };

        List<Integer> probquanList = Arrays.asList(probquanArray);

        Order order = new Order();

        order.setCustomerId("TestCustomerId");
        order.setContactNo("TestContactNo");
        order.setLocation("TestLocation");

        for(int i=0;i<noofitems;i++) {

            Collections.shuffle(probquanList);

            int quantity =probquanList.get(0);

            OrderItem bt = new OrderItem();

            bt.setItemId(String.valueOf(ItemIds[i].getId()));
            bt.setQuantity(String.valueOf(quantity));
            bt.setOrder(order);

            order.getItem().add(bt);
        }

        orderGenerateService.saveOrder(order);

        long orderId = order.getId();

        OrderStatus orderEmp = new OrderStatus();

        orderEmp.setOrderId(orderId);
        orderEmp.setPackingState("0");
        orderEmp.setPickingState("0");
        orderEmp.setShippingState("0");

        orderStatusService.saveOrderEmployee(orderEmp);

    }


    @GetMapping("/getOrderItems/{orderId}")
    public Set<ItemIdOrderDto> getOrderItems(@PathVariable(value = "orderId") long orderId) {

        Set<ItemIdOrderDto> itemIds = new HashSet<>();

        Order order = orderGenerateService.findOrderById(orderId);

        for(OrderItem orditem : order.getItem()) {

            ItemIdOrderDto ido = new ItemIdOrderDto();

            ido.setItemId(orditem.getItemId());

            itemIds.add(ido);
        }

        return itemIds;

    }


    @GetMapping("/getOrderItemsWithWeight/{orderId}")
    public Set<ItemWithWeight> getOrderItemsWithWeight(@PathVariable(value = "orderId") long orderId) {

        Set<ItemWithWeight> itemIds = new HashSet<>();

        Order order = orderGenerateService.findOrderById(orderId);

        for(OrderItem orditem : order.getItem()) {

            ItemWithWeight ido = new ItemWithWeight();

            ido.setItemId(orditem.getItemId());
            ido.setCount(orditem.getQuantity());

            itemIds.add(ido);
        }

        return itemIds;

    }


    @GetMapping("/getTopOrder")
    public long getTopOrder(){

        long orderId = 0;

        List<String> orders = orderStatusService.getNeworders();

        if(orders.size() != 0){
            orderId = Long.parseLong(orders.get(0));
        }

        return orderId;
    }


    @PutMapping("/step")
    public void getStep(@RequestBody StepDto model){

        generateNewOrders();

        step = model.getStep();

    }

    @GetMapping("/step")
    public StepDto getStep(){

        StepDto stepdto = new StepDto();

        stepdto.setStep(step);

        return stepdto;

    }

}
