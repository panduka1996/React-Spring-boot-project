package ie.ucd.rawana.simulatorapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ie.ucd.rawana.simulatorapi.model.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long>{

    @Query("SELECT WK.EmployeeName FROM Worker WK WHERE WK.jobType = 'PIC'")
    List<String> getWorkerIds();

    @Query("SELECT WK.EmployeeName FROM Worker WK")
    List<String> getWorkerIdsForAuths();

    @Query("SELECT WK FROM Worker WK WHERE WK.EmployeeName =?1")
    Worker getWorkerById(String workerName);
}

