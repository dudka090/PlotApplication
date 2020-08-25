package pl.myapplication.plot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDate startDate;
    LocalDate endDate;
    Integer numberOfPeople;
    Integer numberOfCars;
    Boolean guestAccepted;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;


    public Reservation(LocalDate start, LocalDate end, Integer numOfPeople, Integer numOfCars, Boolean guestAccepted, User user) {
        this.startDate = start;
        this.endDate = end;
        this.numberOfPeople = numOfPeople;
        this.numberOfCars = numOfCars;
        this.guestAccepted = guestAccepted;
        this.user = user;
    }
}
