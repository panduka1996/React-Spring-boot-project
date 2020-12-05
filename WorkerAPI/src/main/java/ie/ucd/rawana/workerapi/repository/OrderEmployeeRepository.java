package ie.ucd.rawana.workerapi.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import ie.ucd.rawana.workerapi.model.OrderEmployee;

public interface OrderEmployeeRepository extends JpaRepository<OrderEmployee, Long>{

	@Transactional
	@Modifying
	@Query("UPDATE OrderEmployee OE SET OE.packingEmployee=?2 WHERE OE.orderId=?1")
	void updateEmployeeOrder(long orderId,String packerId);
	
	@Transactional
	@Modifying
	@Query("UPDATE OrderEmployee OE SET OE.shippingEmployee=?2 WHERE OE.orderId=?1")
	void updateShippingEmployeeOrder(long orderId,String packerId);
}
