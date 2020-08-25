package pl.myapplication.plot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.myapplication.plot.model.chat.File;
import pl.myapplication.plot.repository.FileRepository;

@Service
public class FileService {
    FileRepository fileRepository;
    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void save(File file){
        this.fileRepository.save(file);
    }
}
