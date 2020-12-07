package ie.ucd.rawana.simulatorapi;

import ie.ucd.rawana.simulatorapi.DTO.ClockRegistry;
import ie.ucd.rawana.simulatorapi.DTO.ClockResponse;
import ie.ucd.rawana.simulatorapi.controller.WorkerController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SimulatorapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimulatorapiApplication.class, args);

    }

}
