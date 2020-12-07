package ie.ucd.rawana.simulatorapi.serviceImp;

import java.util.List;

import ie.ucd.rawana.simulatorapi.model.Worker;
import ie.ucd.rawana.simulatorapi.repository.WorkerRepository;
import ie.ucd.rawana.simulatorapi.services.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkerServiceImp implements WorkerService {

    @Autowired
    private WorkerRepository workerRepository;

    @Override
    public List<String> getWorkerIds() {

        return workerRepository.getWorkerIds();
    }

    @Override
    public List<String> getWorkerIdsForAuths() {

        return workerRepository.getWorkerIdsForAuths();
    }

    @Override
    public Worker getWorkerById(String workerName) {

        return workerRepository.getWorkerById(workerName);
    }

    //DonPlex
    @Override
    public List<Worker> getAllEmployees() {
        List<Worker> employees = workerRepository.findAll();
        return employees;
    }

    //DonPlex
    @Override
    public void saveEmployee(Worker work) {
        workerRepository.save(work);

    }

    //DonPlex
    @Override
    public void deleteEmployee(long id) {
        workerRepository.deleteById(id);
    }


}
