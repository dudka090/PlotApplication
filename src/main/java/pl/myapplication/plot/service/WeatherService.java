package pl.myapplication.plot.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.myapplication.plot.model.Plot;

import pl.myapplication.plot.model.weather.Weather;
import pl.myapplication.plot.model.weather.Weather15;

import java.time.LocalDate;


@Service
public class WeatherService {
    Plot plot = new Plot();
    ObjectMapper objectMapper = new ObjectMapper();

    public Weather weatherQuery() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.climacell.co/v3/weather/realtime")
                .queryParam("apikey", "HeshQjAHdS0zVvDKIrVUrpNDPXfDWp47")
                .queryParam("unit_system", "si")
                .queryParam("lat", plot.getLATTITUDE())
                .queryParam("lon", plot.getLONGITUDE())
                .queryParam("fields","temp,feels_like,humidity,wind_speed,precipitation,precipitation_type,weather_code");
        ResponseEntity<String> weatherResponse = restTemplate.getForEntity(builder.toUriString(), String.class);
        Weather weatherResponse2 = objectMapper.readValue(weatherResponse.toString().substring(5) , Weather.class);
        return weatherResponse2;
    }

    public Weather15[] weatherQueryFor15Days() throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.climacell.co/v3/weather/forecast/daily")
                .queryParam("apikey", "HeshQjAHdS0zVvDKIrVUrpNDPXfDWp47")
                .queryParam("unit_system", "si")
                .queryParam("lat", plot.getLATTITUDE())
                .queryParam("lon", plot.getLONGITUDE())
                .queryParam("fields","temp,weather_code")
                .queryParam("start_time", "now")
                .queryParam("end_time", LocalDate.now().plusDays(14));
     ResponseEntity<String> weatherResponse = restTemplate.getForEntity(builder.toUriString(), String.class);
        Weather15[] weather15Days = objectMapper.readValue(weatherResponse.toString().substring(5,weatherResponse.toString().length()-426), Weather15[].class);

        return weather15Days;
    }


    public ResponseEntity<String> realTimeWeather() throws JsonProcessingException {
        Weather weather = weatherQuery();
        StringBuilder realTimeWeatherResponse  = new StringBuilder().append("Obecna pogoda na działce:" + weather.getWeather_code().getValue())
                .append("\n")
                .append("Temperatura: " + weather.getTemp().getValue() + "C").append("\n")
                .append("Temperatura odczuwalna: " + weather.getFeels_like().getValue() + "C").append("\n")
                .append("Prędkość wiatru: " +weather.getWind_speed().getValue() + "m/s").append("\n")
                .append("Wilgotność powietrza: " + weather.getHumidity().getValue() + "%").append("\n");
        if(weather.getPrecipitation_type().getValue().equals("none")){
            realTimeWeatherResponse.append("Opady: brak");
        }else{
            realTimeWeatherResponse.append("Opady: " + weather.getPrecipitation_type().getValue()).append("\n")
                    .append("Ilość opadów: " + weather.getPrecipitation().getValue() + "mm/hr");
        }
        return new ResponseEntity<>(realTimeWeatherResponse.toString(), HttpStatus.OK);
    }

    public ResponseEntity<String> weatherFor15Days () throws JsonProcessingException {
        Weather15[] weather = weatherQueryFor15Days();
        StringBuilder forecast  = new StringBuilder().append("Pogoda na 15 dni: \n");
        for (Weather15 w : weather) {
            forecast.append("Data: " + w.getObservation_time().getValue() + "\n")
                    .append("Pogoda: " + w.getWeather_code().getValue() + "\n")
                    .append("Temperatura minimalna: " + w.getTemp()[0].getMin().getValue() + "\n")
                    .append("temperatura maksymalna: " + w.getTemp()[1].getMax().getValue() + "\n");
        }
       return new ResponseEntity<>(forecast.toString(), HttpStatus.OK);
    }
}
