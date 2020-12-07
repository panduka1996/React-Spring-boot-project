package ie.ucd.rawana.ordergenerateapi.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.ucd.rawana.ordergenerateapi.model.Order;
import ie.ucd.rawana.ordergenerateapi.repository.OrderGenerateRepository;
import ie.ucd.rawana.ordergenerateapi.services.OrderGenerateService;

@Service
public class OrderGenerateServiceImp implements OrderGenerateService{

    @Autowired
    private OrderGenerateRepository orderGenerateRepository;

    @Override
    public boolean saveOrder(Order order) {

        boolean feedBack = false;

        try {

            orderGenerateRepository.save(order);

            feedBack = true;
        }
        catch(Exception e) {

            feedBack = false;
        }

        return feedBack;

    }

    @Override
    public Order findOrderById(long OrderId) {

        return orderGenerateRepository.findById(OrderId).get();

    }

}
