package ie.ucd.rawana.workerapi.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import ie.ucd.rawana.workerapi.model.OrderStatus;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long>{

	
	@Query("SELECT OS FROM OrderStatus OS WHERE OS.pickingState = '0'")
	List<OrderStatus> getAllPickerOrders();
	
	@Query("SELECT OS FROM OrderStatus OS WHERE OS.packingState = '0' AND OS.pickingState = '2'")
	List<OrderStatus> getAllPackerOrders();
	
	@Query("SELECT OS FROM OrderStatus OS WHERE OS.shippingState = '0' AND OS.packingState = '2'")
	List<OrderStatus> getAllShipperOrders();
	
	@Transactional
	@Modifying
	@Query("UPDATE OrderStatus OS SET OS.pickingState='1' WHERE OS.orderId=?1")
	void updatePickerOrder(long orderId);
	
	@Transactional
	@Modifying
	@Query("UPDATE OrderStatus OS SET OS.packingState='1' WHERE OS.orderId=?1")
	void updatePackerOrder(long orderId);
	
	@Transactional
	@Modifying
	@Query("UPDATE OrderStatus OS SET OS.shippingState='1' WHERE OS.orderId=?1")
	void updateShipperOrder(long orderId);
	
	@Transactional
	@Modifying
	@Query("UPDATE OrderStatus OS SET OS.pickingState='2' WHERE OS.orderId=?1")
	void updatePickerOrderToComplete(long orderId);
	
	@Transactional
	@Modifying
	@Query("UPDATE OrderStatus OS SET OS.packingState='2' WHERE OS.orderId=?1")
	void updatePackerOrderToComplete(long orderId);
	
	@Transactional
	@Modifying
	@Query("UPDATE OrderStatus OS SET OS.shippingState='2' WHERE OS.orderId=?1")
	void updateShipperOrderToComplete(long orderId);
	
	@Query("SELECT OS.orderId FROM OrderStatus OS INNER JOIN OrderEmployee OE ON OS.orderId = OE.orderId WHERE OE.pickingEmployee = ?1 and OS.pickingState = '1'")
	List<String> getPickerOrders(String pickerId);
	
	@Query("SELECT OS.orderId FROM OrderStatus OS INNER JOIN OrderEmployee OE ON OS.orderId = OE.orderId WHERE OE.packingEmployee = ?1 and OS.packingState = '1'")
	List<String> getPackerOrders(String packerId);
	
	@Query("SELECT OS.orderId FROM OrderStatus OS INNER JOIN OrderEmployee OE ON OS.orderId = OE.orderId WHERE OE.shippingEmployee = ?1 and OS.shippingState = '1'")
	List<String> getShipperOrders(String packerId);
	
	@Query("SELECT COUNT(OS.orderId) FROM OrderStatus OS INNER JOIN OrderEmployee OE ON OS.orderId = OE.orderId WHERE OE.pickingEmployee=?1 and OS.pickingState = '2' GROUP BY OE.pickingEmployee")
	String getCompletedOrderCount(String pickerId);
	
	@Query("SELECT COUNT(OS.orderId) FROM OrderStatus OS INNER JOIN OrderEmployee OE ON OS.orderId = OE.orderId WHERE OE.pickingEmployee=?1 and OS.pickingState = '1' GROUP BY OE.pickingEmployee")
	String getRunningOrderCount(String pickerId);
	
}
