package ie.ucd.rawana.workerapi.DTO;

import java.util.HashSet;
import java.util.Set;

public class WorkerSetDto {

    private Set<WorkerDto> workers = new HashSet<>();

    public WorkerSetDto() {
    }

    public Set<WorkerDto> getWorkers() {
        return workers;
    }

    public void setWorkers(Set<WorkerDto> workers) {
        this.workers = workers;
    }
}
