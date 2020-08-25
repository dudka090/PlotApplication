package pl.myapplication.plot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.myapplication.plot.model.ToDo;
import pl.myapplication.plot.service.ToDoService;

import java.util.List;

@RestController
@RequestMapping("/todoList")
public class ToDoController {
   ToDoService toDoService;

@Autowired
    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @PostMapping("/initial")
    public String createInitialData() {
        ToDo todo1 = new ToDo("odkurzyć");
        ToDo todo2  = new ToDo("pozmywać");
        ToDo todo3 = new ToDo("przygotować opał");
        this.toDoService.save(todo1);
        this.toDoService.save(todo2);
        this.toDoService.save(todo3);
        return "Dane stworzone ";
    }

    @GetMapping("/getAll")
    public ResponseEntity<String> findAll() {
        return this.toDoService.findAll();
    }

    @GetMapping("/getByLogin")
    public List<String> findAllByUser_Login(@RequestParam String login) {
        return this.toDoService.findAllByUser_Login(login);
    }

    @PostMapping("/save")
    public ToDo save(@RequestBody ToDo toDo) {
        return this.toDoService.save(toDo);

    }

    @PostMapping("/addUserToToDo")
    public ResponseEntity<String> addUserToToDo(@RequestParam Long id, @RequestParam String login) {
        return this.toDoService.addUserToToDo(id, login);
    }

    @PostMapping("/deleteUserFromToDo/{id}")
    public ResponseEntity<String> deleteUserFromToDo(@PathVariable Long id) {
        return this.toDoService.deleteUserFromTodo(id);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return this.toDoService.delete(id);
    }

    @PostMapping("/changeName/{id}/{name}")
    public ResponseEntity<String> changeName(@PathVariable Long id, @PathVariable String name) {
        return this.toDoService.changeName(id, name);
    }
}
