package ie.ucd.rawana.simulatorapi.repository;

import ie.ucd.rawana.simulatorapi.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ie.ucd.rawana.simulatorapi.model.Product;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

    @Query("SELECT PD.weight FROM Product PD WHERE PD.id =?1")
    String getItemWeight(long itemId);

    @Query("SELECT PD.location FROM Product PD WHERE PD.id =?1")
    String getItemLocation(long itemId);

    public List<Product> findProductById(long id);
}
