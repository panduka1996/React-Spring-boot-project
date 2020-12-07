package ie.ucd.rawana.ordergenerateapi.services;

import ie.ucd.rawana.ordergenerateapi.model.Order;

public interface OrderGenerateService {

    public boolean saveOrder(Order order);

    public Order findOrderById(long OrderId);

}