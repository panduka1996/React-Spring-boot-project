package ie.ucd.rawana.simulatorapi.services;

import ie.ucd.rawana.simulatorapi.model.Worker;

import java.util.List;

public interface WorkerService {

    public List<String> getWorkerIds();

    public List<String> getWorkerIdsForAuths();

    public Worker getWorkerById(String workerName);

    //DonPlex
    public void deleteEmployee(long id);

    //DonPlex
    public void saveEmployee(Worker work);

    //DonPlex
    public List<Worker> getAllEmployees();
}
