package ie.ucd.rawana.ordergenerateapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.ucd.rawana.ordergenerateapi.model.Order;

public interface OrderGenerateRepository extends JpaRepository<Order, Long>{

}
