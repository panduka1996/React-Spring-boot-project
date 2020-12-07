package ie.ucd.rawana.ordergenerateapi.services;

import java.util.List;

import ie.ucd.rawana.ordergenerateapi.model.OrderStatus;

public interface OrderStatusService {

    public boolean saveOrderEmployee(OrderStatus orderemployee);

    public List<OrderStatus> getAllOrders();

    public OrderStatus getOrderById(long orderId);

    public List<String> getNeworders();

}
