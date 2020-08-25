package pl.myapplication.plot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.myapplication.plot.PlotApplication;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= PlotApplication.class)
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    public void login() throws JsonProcessingException {
        assertEquals(HttpStatus.BAD_REQUEST,this.userService.login("login", "passw").getStatusCode() );

    }

    @Test
    public void remindLoginData() {
        assertEquals(HttpStatus.NOT_FOUND, this.userService.remindLoginData("xxx@xxx.pl").getStatusCode());
    }
}
