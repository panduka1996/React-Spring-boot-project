package ie.ucd.rawana.simulatorapi.repository;

import ie.ucd.rawana.simulatorapi.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MapRepository extends JpaRepository<Location, Long> {

    @Query("SELECT LC.location FROM Location LC")
    String[] getLocation();

    @Query("SELECT LC FROM Location LC WHERE LC.id =?1")
    Location getVerticeDetails(long id);
}
