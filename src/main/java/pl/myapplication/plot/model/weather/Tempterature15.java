package pl.myapplication.plot.model.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tempterature15 {
    String observation_time;
    Temperature min;
    Temperature max;
}
