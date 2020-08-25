package pl.myapplication.plot.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.myapplication.plot.model.chat.File;
import pl.myapplication.plot.model.chat.Message;
import pl.myapplication.plot.repository.MessageRepository;
import pl.myapplication.plot.utils.StorageException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class MessageService {
    MessageRepository messageRepository;
    FileService fileService;

    @Autowired
    public MessageService(MessageRepository messageRepository, FileService fileService) {
        this.messageRepository = messageRepository;
        this.fileService = fileService;
    }

    public ResponseEntity<String> saveText(Message message){
        this.messageRepository.save(message);
        return new ResponseEntity<>("Zapisano wiadomość" , HttpStatus.OK);
    }

    public ResponseEntity<String> saveFile(MultipartFile file){
       String path = "C:\\Users\\ewaga\\Desktop\\CS\\ApplicationFiles\\";
        if (file.isEmpty()) {
            throw new StorageException("Załadowano pusty plik");
        }
        try {
            String fileName = file.getOriginalFilename();
            InputStream is = file.getInputStream();
            Files.copy(is, Paths.get(path + fileName),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            String msg = "Nie udało się zapisać pliku " + file.getOriginalFilename();
            throw new StorageException(msg, e);
        }
        this.fileService.save(new File(file.getOriginalFilename()));
        this.messageRepository.save(new Message(file.getOriginalFilename()));
        return new ResponseEntity<>("Poprawnie zapisano plik " + file.getName(), HttpStatus.OK);
    }

    public ResponseEntity<String> findAll() {
        List<Message> messages =  this.messageRepository.findAll();
        StringBuilder str = new StringBuilder();
        for (Message m : messages) {
            if(m.getMessage().isEmpty()){
                str.append(m.getFile().getName()).append("\n");
            }else{
                str.append(m.getMessage()).append("\n");
            }
        }
        return new ResponseEntity<>(str.toString(), HttpStatus.OK);
    }

}
