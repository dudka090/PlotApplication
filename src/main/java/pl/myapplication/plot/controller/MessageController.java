package pl.myapplication.plot.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.myapplication.plot.model.chat.Message;
import pl.myapplication.plot.service.MessageService;
import pl.myapplication.plot.utils.StorageException;



@RestController
@RequestMapping("/message")
public class MessageController {
    MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/text")
    public ResponseEntity<String> save(@RequestBody String text){
        return this.messageService.saveText(new Message(text));
    }

    @PostMapping(value = "/file", consumes = {"multipart/form-data"})
    public ResponseEntity<String> save(@RequestBody MultipartFile file){
       return this.messageService.saveFile(file);
    }

    @GetMapping("/getAll")
    public ResponseEntity<String> getAll() {
        return this.messageService.findAll();
    }

    @ExceptionHandler(StorageException.class)
    public void handleStorageFileNotFound(StorageException e) {
        System.out.println("Wystąpił błąd" +  e);

    }

}
