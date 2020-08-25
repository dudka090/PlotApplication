package pl.myapplication.plot.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.myapplication.plot.model.User;
import pl.myapplication.plot.repository.UserRepository;
import pl.myapplication.plot.utils.LoggedCheck;
import java.util.*;

@Service
public class UserService {
    UserRepository userRepository;
    EmailService emailService;
    LoggedCheck loggedCheck;
    WeatherService weatherService;

@Autowired
    public UserService(UserRepository userRepository, EmailService emailService, LoggedCheck loggedCheck, WeatherService weatherService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.loggedCheck = loggedCheck;
        this.weatherService = weatherService;
    }

    public ResponseEntity<String> login (String login, String passoword) throws JsonProcessingException {
        if(this.userRepository.existsUserByLogin(login)){
           Optional<User> user = this.userRepository.findUserByLogin(login);
            if(passoword.equals(user.get().getPassword())){
                String token = Jwts.builder()
                        .setSubject(login)
                        .claim("roles", "user")
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + 30000))
                        .signWith(SignatureAlgorithm.HS512, login+passoword).compact();
                this.loggedCheck.insertLoggedPerson(login, new Date(System.currentTimeMillis() + 30000));
                StringBuilder resp = new StringBuilder().append("Zalogowano \n  Token: " +  token)
                        .append(this.weatherService.realTimeWeather().toString());
                return new ResponseEntity<>("Zalogowano \n " +  token, HttpStatus.ACCEPTED);
            }
            else{
                return new ResponseEntity<>("Złe dane logowania", HttpStatus.BAD_REQUEST);
            }
        } else{
            return new ResponseEntity<>("Użytkownik o podanym loginie nie istnieje", HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<String> save (User user){
        if(userRepository.existsUserByLogin(user.getLogin()) || userRepository.existsUserByEmail(user.getEmail())){
            return new ResponseEntity<>("Użytkownik o podanym loginie lub emailu istnieje.", HttpStatus.CONFLICT);
        } else {
            this.userRepository.save(user);
            String content = "\nDane do logowania: \n Login: " + user.getLogin() + "\n Hasło: " + user.getPassword() + "\n Nazwa wyświetlana: " + user.getDisplayName();
           emailService.sendEmail(user.getEmail(), "Utworzono nowego użytkownika",content);
            return new ResponseEntity<>("Utworzono poprawnie konto.", HttpStatus.CREATED);

        }
    }

    public ResponseEntity<String> remindLoginData (String email){
        if(this.userRepository.existsUserByEmail(email)){
          User user = this.userRepository.findUserByEmail(email).get();
          String content = "Dane do logowania: \nLogin: " + user.getLogin() + "\nHasło: " + user.getPassword();
            emailService.sendEmail(email, "Dane do logownia",content);
        return new ResponseEntity<>("Wysłano wiadomość z danymi do logowania", HttpStatus.OK);
        }
        return new ResponseEntity<>("Użytkownik o podanym e-mailu nie istnieje", HttpStatus.NOT_FOUND);
    }

}
