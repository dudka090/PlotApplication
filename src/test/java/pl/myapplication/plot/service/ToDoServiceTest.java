package pl.myapplication.plot.service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.myapplication.plot.PlotApplication;
import pl.myapplication.plot.model.ToDo;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= PlotApplication.class)
public class ToDoServiceTest {
    @Autowired
    ToDoService toDoService;

    @Test
    public void findAll() {
        ResponseEntity<String> test = this.toDoService.findAll();
        assertEquals(HttpStatus.OK, test.getStatusCode());
    }

    @Test
    public void save() {
        ToDo testTodo = new ToDo("Odkurzyć");
        ToDo result = this.toDoService.save(testTodo);
        assertEquals(testTodo, result);
    }

    @Test
    public void addUserToToDo() {
        ResponseEntity<String> test = this.toDoService.addUserToToDo(Long.valueOf(1), "loginTest");
        assertEquals(HttpStatus.BAD_REQUEST, test.getStatusCode());
    }

    @Test
    public void deleteUserFromToDo() {
        ResponseEntity<String> test = this.toDoService.deleteUserFromTodo(Long.valueOf(1));
        assertEquals(HttpStatus.BAD_REQUEST, test.getStatusCode());
    }

    @Test
    public void delete() {
        ResponseEntity<String> test = this.toDoService.delete(Long.valueOf(1));
        assertEquals(HttpStatus.BAD_REQUEST, test.getStatusCode());
    }

    @Test
    public void changeName() {
        ResponseEntity<String> test = this.toDoService.changeName(Long.valueOf(1), "login1");
        assertEquals(HttpStatus.BAD_REQUEST, test.getStatusCode());
    }

}
