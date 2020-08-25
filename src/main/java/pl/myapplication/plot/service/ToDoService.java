package pl.myapplication.plot.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.myapplication.plot.model.ToDo;
import pl.myapplication.plot.model.User;
import pl.myapplication.plot.repository.ToDoRepository;
import pl.myapplication.plot.repository.UserRepository;
import pl.myapplication.plot.utils.LoggedCheck;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ToDoService {
    ToDoRepository toDoRepository;
    UserRepository userRepository;
    LoggedCheck loggedCheck;


    @Autowired
    public ToDoService(ToDoRepository toDoRepository, UserRepository userRepository, LoggedCheck loggedCheck) {
        this.toDoRepository = toDoRepository;
        this.userRepository = userRepository;
        this.loggedCheck = loggedCheck;
    }

    public ResponseEntity<String> findAll(){
        String result = this.toDoRepository.findAll().stream()
                .map(x -> String.valueOf(x))
                .collect(Collectors.joining(","));
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    public List<String> findAllByUser_Login(String login){
        Optional<List<ToDo>> toDos = this.toDoRepository.findAllByUser_Login(login);
        List<String> todoByLogin = new ArrayList<>();
        todoByLogin.add("Lista zadań dla: " + login);
        for (ToDo todo: toDos.get()
             ) {
            todoByLogin.add(todo.getId() + ". " + todo.getName());
        }
        return todoByLogin;
    }

    public ToDo save(ToDo toDo){
        return this.toDoRepository.save(toDo);
    }

    public ResponseEntity<String> addUserToToDo(Long id, String userLogin){
        if(this.loggedCheck.checkWhoIsLogged(userLogin)){
            if(this.toDoRepository.findById(id).get().getUser() == null){
                User user = this.userRepository.findUserByLogin(userLogin).get();
                this.toDoRepository.addUserToToDo(id, user);
                return new ResponseEntity<>(this.toDoRepository.findById(id).get().getName() + " zostało przyisane do "
                        + user.getDisplayName(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Podane zadanie ma już przypisanego użytkownika", HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>("Aby przypisać siebie do zadania musisz być zalogowany", HttpStatus.BAD_REQUEST);
        }


    }

    public ResponseEntity<String> deleteUserFromTodo(Long id){
        Optional<ToDo> toDo = this.toDoRepository.findById(id);
        if(this.toDoRepository.existsById(id)){
            if(toDo.get().getUser() != null){
                if(this.loggedCheck.checkWhoIsLogged(toDo.get().getUser().getLogin())){
                    this.toDoRepository.deleteUserFromTodo(id);
                    return new ResponseEntity<>("Użytkownik został usunięty", HttpStatus.OK);
                }else{
                    return new ResponseEntity<>("Użytkownik przypisany do zadania może " +
                            "być usunięty tylko przez tego użytkownika.", HttpStatus.BAD_REQUEST);
                }

            }else{
                return new ResponseEntity<>("Podane zadanie nie posiada przypisanego użytkownika", HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>("Nie istnieje zadanie o podanym id", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> delete (long id){
        if(this.toDoRepository.existsById(id)){
            ToDo toDo = this.toDoRepository.findById(id);
            if(toDo.getUser() == null){
                this.toDoRepository.delete(toDo);
                return new ResponseEntity<>("Zadanie usunięte", HttpStatus.OK);
            }else if(this.loggedCheck.checkWhoIsLogged(toDo.getUser().getLogin())){
                this.toDoRepository.delete(toDo);
                return new ResponseEntity<>("Zadanie usunięte", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Tylko użytkownik przypisany do zadania może je usunąć", HttpStatus.BAD_REQUEST);
            }
        }else{

            return new ResponseEntity<>("Nie istnieje zadanie o podanym id", HttpStatus.BAD_REQUEST);
        }
        }

    public ResponseEntity<String> changeName (Long id, String name){
        if(this.toDoRepository.existsById(id)){
            ToDo todo = this.toDoRepository.findById(id).get();
            if(todo.getUser()==null || this.loggedCheck.checkWhoIsLogged(todo.getUser().getLogin())){
                this.toDoRepository.changeName(id, name);
                return new ResponseEntity<>("Zmieniono " + todo.getName() + " na " + name, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Zmienić nazwe może tylko użytkownik przypisany do zadania.", HttpStatus.OK);
            }
        }else{
            return new ResponseEntity<>("Nie istnieje zadanie o podanym id", HttpStatus.BAD_REQUEST);
        }

    }

}
