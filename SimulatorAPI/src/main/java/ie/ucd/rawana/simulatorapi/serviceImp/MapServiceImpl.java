package ie.ucd.rawana.simulatorapi.serviceImp;

import ie.ucd.rawana.simulatorapi.model.Location;
import ie.ucd.rawana.simulatorapi.repository.MapRepository;
import ie.ucd.rawana.simulatorapi.services.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MapServiceImpl implements MapService {

    @Autowired
    private MapRepository mapRepository;

    @Override
    public String[] getLocation() {

       return mapRepository.getLocation();
    }

    @Override
    public Location getVerticeDetails(long id) {

        return mapRepository.getVerticeDetails(id);
    }
}
