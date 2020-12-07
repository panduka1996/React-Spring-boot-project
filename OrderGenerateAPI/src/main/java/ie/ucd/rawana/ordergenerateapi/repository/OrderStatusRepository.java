package ie.ucd.rawana.ordergenerateapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ie.ucd.rawana.ordergenerateapi.model.OrderStatus;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long>{

    @Query("SELECT OS FROM OrderStatus OS")
    List<OrderStatus> getAllOrders();

    @Query("SELECT OS.orderId FROM OrderStatus OS WHERE OS.pickingState = '0'")
    List<String> getNeworders();

}
