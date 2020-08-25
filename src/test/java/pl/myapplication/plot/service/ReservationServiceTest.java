package pl.myapplication.plot.service;
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
public class ReservationServiceTest {
    @Autowired
    ReservationService reservationService;

    @Test
    public void save() {
        ResponseEntity<String> test = this.reservationService.save("22-08-2020", "26-08-2020",
                4, 2, true, "login" );
        assertEquals(HttpStatus.BAD_REQUEST, test.getStatusCode());

    }

    @Test
    public void deleteById() {
        ResponseEntity<String> test = this.reservationService.deleteById(Long.valueOf(1));
        assertEquals(HttpStatus.BAD_REQUEST, test.getStatusCode());
    }

    @Test
    public void findAllByLogin() {
        ResponseEntity<String> test = this.reservationService.findAllByLogin("login");
        assertEquals(HttpStatus.BAD_REQUEST, test.getStatusCode());
    }

    @Test
    public void findAll() {
        assertEquals(HttpStatus.OK, this.reservationService.findAll().getStatusCode());
    }

    @Test
    public void checkAvailability() {
        assertEquals("Termin wolny", this.reservationService.checkAvailability("20-05-2020", "25-05-2020"));
    }

    @Test
    public void whoHasTheKeys() {
        assertEquals("Nie wiadomo kto ma klucze.", this.reservationService.whoHasTheKeys().getBody());
    }
}
