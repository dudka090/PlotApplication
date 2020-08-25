package pl.myapplication.plot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.myapplication.plot.model.chat.File;


@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    File save(File file);
}
