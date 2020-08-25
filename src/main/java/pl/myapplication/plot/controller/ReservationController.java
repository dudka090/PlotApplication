package pl.myapplication.plot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.myapplication.plot.service.ReservationService;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
    ReservationService reservationService;


    @Autowired
    public ReservationController( ReservationService reservationService) {
        this.reservationService = reservationService;

    }

    @PostMapping("/reserve")
    public ResponseEntity<String> reserve(@RequestParam String startDate,
                                  @RequestParam String endDate,
                                  @RequestParam Integer numOfPeople,
                                  @RequestParam Integer numOfCars,
                                  @RequestParam Boolean guestAccepted,
                                  @RequestParam String login){
        return this.reservationService.save(startDate,endDate,numOfPeople,numOfCars,guestAccepted,login);
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<String> cancel(@PathVariable long id){
        return this.reservationService.deleteById(id);
    }

    @PostMapping("findByLogin/{login}")
    public ResponseEntity<String> findByLogin(@PathVariable String login){
      return this.reservationService.findAllByLogin(login);
    }

    @GetMapping("/checkAvailability")
    public String chceckAvailability(@RequestParam String startDate,
                                   @RequestParam(required = false) String endDate){
        return this.reservationService.checkAvailability(startDate, endDate);
    }

    @GetMapping("/getAll")
    public ResponseEntity<String> getAll() {
        return this.reservationService.findAll();
    }




}
