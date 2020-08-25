package pl.myapplication.plot.model.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weather {
    Long lat;
    Long lon;
    Temperature temp;
    FeelsLike feels_like;
    WindSpeed wind_speed;
    Humidity humidity;
    Precipitation precipitation;
    PrecipitationType precipitation_type;
    WeatherCode weather_code;
    ObservationTime observation_time;
}
