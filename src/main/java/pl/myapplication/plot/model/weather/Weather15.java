package pl.myapplication.plot.model.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weather15 {
    Long lat;
    Long lon;
    WeatherCode weather_code;
    Tempterature15[] temp;
    ObservationTime observation_time;

}
