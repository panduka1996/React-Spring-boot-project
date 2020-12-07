package ie.ucd.rawana.ordergenerateapi.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.ucd.rawana.ordergenerateapi.model.OrderStatus;
import ie.ucd.rawana.ordergenerateapi.repository.OrderStatusRepository;
import ie.ucd.rawana.ordergenerateapi.services.OrderStatusService;

@Service
public class OrderStatusServiceImp implements OrderStatusService{

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Override
    public List<OrderStatus> getAllOrders() {

        return orderStatusRepository.getAllOrders();
    }

    @Override
    public OrderStatus getOrderById(long orderId) {

        return orderStatusRepository.findById(orderId).get();
    }

    @Override
    public boolean saveOrderEmployee(OrderStatus orderemployee) {


        boolean feedBack = false;

        try {

            orderStatusRepository.save(orderemployee);

            feedBack = true;
        }
        catch(Exception e) {

            feedBack = false;
        }

        return feedBack;
    }

    @Override
    public List<String> getNeworders() {

        return orderStatusRepository.getNeworders();
    }

}
