package ie.ucd.rawana.ordergenerateapi;

import ie.ucd.rawana.ordergenerateapi.DTO.ClockRegistry;
import ie.ucd.rawana.ordergenerateapi.DTO.ClockResponse;
import ie.ucd.rawana.ordergenerateapi.DTO.ItemIdDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class OrdergenerateapiApplication {

    public static void main(String[] args) {

        SpringApplication.run(OrdergenerateapiApplication.class, args);

    }

}
