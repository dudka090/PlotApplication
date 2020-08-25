package pl.myapplication.plot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.myapplication.plot.model.User;
import pl.myapplication.plot.service.UserService;
import pl.myapplication.plot.utils.LoggedCheck;

@RestController
@RequestMapping("/user")
public class UserController {
    UserService userService;
    LoggedCheck loggedCheck;

    @Autowired
    public UserController(UserService userService, LoggedCheck loggedCheck) {
        this.userService = userService;
        this.loggedCheck = loggedCheck;
    }

    @PostMapping("/create")
    public ResponseEntity<String> save (@RequestBody User user){
        return this.userService.save(user);
    }

    @PostMapping("/login/{login}/{password}")
    public ResponseEntity<String> login (@PathVariable String login, @PathVariable String password){
        try {
           return this.userService.login(login, password);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("dsg", HttpStatus.NOT_FOUND);

    }

    @GetMapping("/remind")
    public ResponseEntity<String> remindLoginData (@RequestParam String email){
        return this.userService.remindLoginData(email);
    }

    }

