package pl.myapplication.plot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.myapplication.plot.model.Reservation;
import pl.myapplication.plot.model.User;
import pl.myapplication.plot.repository.ReservationRepository;
import pl.myapplication.plot.repository.UserRepository;
import pl.myapplication.plot.utils.LoggedCheck;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    ReservationRepository reservationRepository;
    LoggedCheck loggedCheck;
    UserRepository userRepository;
    EmailService emailService;


    @Autowired
    public ReservationService(ReservationRepository reservationRepository, LoggedCheck loggedCheck, UserRepository userRepository, EmailService emailService) {
        this.reservationRepository = reservationRepository;
        this.loggedCheck = loggedCheck;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public ResponseEntity<String> save(String startDate, String endDate, Integer numOfPeople,
                                       Integer numOfCars, Boolean guestAccepted, String login) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate start = LocalDate.parse(startDate,formatter);
        LocalDate end = LocalDate.parse(endDate,formatter);
        if(this.loggedCheck.checkWhoIsLogged(login)){
            User user = this.userRepository.findUserByLogin(login).get();
            Reservation reservation = new Reservation(start,end,numOfPeople,numOfCars, guestAccepted, user);
            Optional<List<Reservation>>  reservations = this.reservationRepository.checkAvailability(start,end);
            if(reservations.isPresent()){
                StringBuilder responseText = new StringBuilder().append("Termin z rezerwacjami z gośćmi: \n");
                for (Reservation r: reservations.get()
                ) {
                    if(r.getGuestAccepted()){
                        responseText.append("Rezerwujący: " + r.getUser().getDisplayName() + " \n Data: " + r.getStartDate() + " - " + r.getEndDate());

                    }else{
                        return new ResponseEntity<>("Termin zajęty", HttpStatus.BAD_REQUEST);
                    }
                }
                this.reservationRepository.save(reservation);
                this.emailService.sendEmail(user.getEmail(), "Potwierdzenie rezerwacji", reservation.toString() + "\n " + responseText.toString());
                return new ResponseEntity<>("Rezerwacja stowrzona\n" + responseText.toString(), HttpStatus.OK);

            }else{
                this.reservationRepository.save(reservation);
                this.emailService.sendEmail(user.getEmail(), "Potwierdzenie rezerwacji", reservation.toString());
                return new ResponseEntity<>("Rezerwacja stowrzona", HttpStatus.OK);
            }


       }
        else{
           return new ResponseEntity<>("Aby zrobić rezerwację musisz być zalogowany", HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<String> deleteById(Long id){
        if(this.reservationRepository.existsById(id)) {
            Reservation reservation = this.reservationRepository.findById(id).get();
            String login = reservation.getUser().getLogin();
            if (this.loggedCheck.checkWhoIsLogged(login)) {
                this.reservationRepository.deleteById(id);
                this.emailService.sendEmail(reservation.getUser().getEmail(), "Rezerwacja usunięta", reservation.toString());
                return new ResponseEntity<>("Rezerwacja usunięta", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Aby usunąć rezerwację musisz być zalogowany", HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>("Nie istnieje rezerwacja o podanym id", HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<String> findAllByLogin(String login){
        if (this.loggedCheck.checkWhoIsLogged(login)){
            Optional<List<Reservation>> reservations = this.reservationRepository.findAllByUser_LoginAndStartDateGreaterThanOrderByStartDateAsc(login, LocalDate.now());
            if(reservations.isPresent()){
                return new ResponseEntity<>(reservations.get().get(0).toString(), HttpStatus.OK);
            }else {
                return new ResponseEntity<>("Nie ma żdanych rezerwacji", HttpStatus.OK);
            }
        }else{
            return new ResponseEntity<>("Aby wyświetlić rezerwacje musisz być zalogowany", HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<String> findAll(){
        return new ResponseEntity<>(this.reservationRepository.findAll().toString(), HttpStatus.OK);}

    public String checkAvailability(String startDate, String endDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate start = LocalDate.parse(startDate,formatter);
        LocalDate end = LocalDate.parse(endDate,formatter);
        Optional<List<Reservation>>  reservations = this.reservationRepository.checkAvailability(start,end);
        if(reservations.isPresent()){
           StringBuilder responseText = new StringBuilder().append("Termin z rezerwacjami z gośćmi: \n");
            for (Reservation r: reservations.get()
                 ) {
                if(r.getGuestAccepted()){
                    responseText.append("Rezerujący: " + r.getUser().getDisplayName() + " \n Data: " + r.getStartDate() + " - " + r.getEndDate());

                }else{
                    return "Termin zajęty";
                }
            }
            return responseText.toString();
        }else{
            return "Termin wolny";
        }

}

    public ResponseEntity<String> whoHasTheKeys(){
        Optional<List<Reservation>> reservations = this.reservationRepository.findAllByEndDateLessThanOrderByEndDateDesc(LocalDate.now());
        if(reservations.isPresent()){
            return new ResponseEntity<>("Klucze ma: " + reservations.get().get(0).getUser().getDisplayName(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Nie wiadomo kto ma klucze.", HttpStatus.OK);
        }
    }

}