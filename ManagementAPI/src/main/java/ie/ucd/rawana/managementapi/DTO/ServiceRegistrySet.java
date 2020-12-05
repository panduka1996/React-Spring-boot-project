package ie.ucd.rawana.managementapi.DTO;

import java.util.*;

public class ServiceRegistrySet {

    private List<ServiceRegistry> registrations = new ArrayList<ServiceRegistry>();

    public ServiceRegistrySet() {
    }

    public List<ServiceRegistry> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<ServiceRegistry> registrations) {
        this.registrations = registrations;
    }
}
