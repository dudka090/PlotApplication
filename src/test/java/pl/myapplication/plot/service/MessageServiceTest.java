package pl.myapplication.plot.service;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.myapplication.plot.PlotApplication;
import pl.myapplication.plot.model.chat.Message;
import pl.myapplication.plot.utils.StorageException;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= PlotApplication.class)
public class MessageServiceTest {
    @Autowired
    MessageService messageService;
    MockMultipartFile mockFile;

    @Before
    public void before() {
        byte[] emptyByte = new byte[0];
        mockFile = new MockMultipartFile("test", emptyByte);
    }

    @Test
    public void saveTextTest() {
        ResponseEntity<String> test = this.messageService.saveText(new Message("Wiadomość próbna"));
        assertEquals("Zapisano wiadomość",test.getBody());
    }


    @Test(expected = StorageException.class)
    public void saveFileTest() {
        this.messageService.saveFile(mockFile);
    }

    @Test
    public void findAll() {
        ResponseEntity<String> test = this.messageService.findAll();
        assertEquals(HttpStatus.OK, test.getStatusCode());
    }
}
