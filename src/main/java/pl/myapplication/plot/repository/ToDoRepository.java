package pl.myapplication.plot.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.myapplication.plot.model.ToDo;
import pl.myapplication.plot.model.User;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    List<ToDo> findAll();

    Optional<List<ToDo>> findAllByUser_Login(String userLogin);

    ToDo save(ToDo todo);

    @Modifying
    @Transactional
    @Query("UPDATE ToDo t SET t.user=?2 WHERE t.id=?1")
    void addUserToToDo(Long id, User user);

    @Modifying
    @Transactional
    @Query("UPDATE ToDo t SET t.user=null WHERE t.id=?1")
    void deleteUserFromTodo(Long id);

    ToDo findById(long id);

    void delete(ToDo todo);

    @Modifying
    @Transactional
    @Query("UPDATE ToDo t SET t.name=?2 WHERE t.id=?1")
    void changeName (Long id, String name);









}

