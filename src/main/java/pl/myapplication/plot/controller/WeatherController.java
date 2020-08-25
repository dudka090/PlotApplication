package pl.myapplication.plot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.myapplication.plot.service.WeatherService;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/now")
    public ResponseEntity<String> realTimeWeather() {
        try {
            ResponseEntity<String> test = this.weatherService.realTimeWeather();
            System.out.println(test.getStatusCode());
            return test;
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Problem z połączeniem z serwerem pogodowym", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/15daysWeather")
    public ResponseEntity<String> weatherFor15Days(){
        try {
            return this.weatherService.weatherFor15Days();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Błąd", HttpStatus.NOT_FOUND);
        }

    }
}
