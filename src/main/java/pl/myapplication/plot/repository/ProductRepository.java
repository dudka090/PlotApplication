package pl.myapplication.plot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.myapplication.plot.model.Product;
import pl.myapplication.plot.model.User;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll();

    Optional<List<Product>> findAllByUser_Login(String userLogin);

    Product save(Product product);

    @Modifying
    @Transactional
    @Query("UPDATE Product t SET t.user=?2 WHERE t.id=?1")
    void addUserToTProduct(Long id, User user);

    @Modifying
    @Transactional
    @Query("UPDATE Product t SET t.user=null WHERE t.id=?1")
    void deleteUserFromProduct(Long id);

    Product findById(long id);

    void delete(Product product);

    @Modifying
    @Transactional
    @Query("UPDATE Product t SET t.name=?2 WHERE t.id=?1")
    void changeName (Long id, String name);


}
