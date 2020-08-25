package pl.myapplication.plot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.myapplication.plot.model.chat.Message;
import java.util.List;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Message save (Message message);
    List<Message> findAll ();

}
