package ie.ucd.rawana.simulatorapi.services;

import ie.ucd.rawana.simulatorapi.model.Location;

import java.util.List;

public interface MapService {

    public String[] getLocation();

    public Location getVerticeDetails(long id);
}
