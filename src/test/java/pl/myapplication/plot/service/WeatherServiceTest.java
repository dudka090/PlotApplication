package pl.myapplication.plot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.myapplication.plot.PlotApplication;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= PlotApplication.class)
public class WeatherServiceTest {
    @Autowired
    WeatherService weatherService;

    @Test
    public void realTimeWeather() throws JsonProcessingException {
        ResponseEntity<String> test = this.weatherService.realTimeWeather();
        assertEquals(HttpStatus.OK, test.getStatusCode());
    }

    @Test
    public void weatherFor15Days() throws JsonProcessingException {
        ResponseEntity<String> test = this.weatherService.weatherFor15Days();
        assertEquals(HttpStatus.OK, test.getStatusCode());
    }
}
