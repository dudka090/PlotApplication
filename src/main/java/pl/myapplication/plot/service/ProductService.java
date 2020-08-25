package pl.myapplication.plot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.myapplication.plot.model.Product;
import pl.myapplication.plot.model.User;
import pl.myapplication.plot.repository.ProductRepository;
import pl.myapplication.plot.repository.UserRepository;
import pl.myapplication.plot.utils.LoggedCheck;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    ProductRepository productRepository;
    UserRepository userRepository;
    LoggedCheck loggedCheck;

    @Autowired
    public ProductService(ProductRepository productRepository, UserRepository userRepository, LoggedCheck loggedCheck) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.loggedCheck = loggedCheck;
    }

    public ResponseEntity<String> findAll() {
        String result = this.productRepository.findAll().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    public List<String> findAllByUser_Login(String login) {
        Optional<List<Product>> products = this.productRepository.findAllByUser_Login(login);
        List<String> productByLogin = new ArrayList<>();
        productByLogin.add("Lista zakupów dla: " + login);
        if(products.isPresent()){
            for (Product product : products.get()
            ) {
                productByLogin.add(product.getId() + ". " + product.getName());
            }
        }
        return productByLogin;
    }

    public Product save(Product product) {
        return this.productRepository.save(product);
    }

    public ResponseEntity<String> addUserToProduct(Long id, String userLogin) {
        if (this.loggedCheck.checkWhoIsLogged(userLogin)) {
            if (this.productRepository.findById(id).get().getUser() == null) {
                User user = this.userRepository.findUserByLogin(userLogin).get();
                this.productRepository.addUserToTProduct(id, user);
                return new ResponseEntity<>(this.productRepository.findById(id).get().getName() + " zostało przyisane do "
                        + user.getDisplayName(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Podany produkt ma już przypisanego użytkownika", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Aby przypisać siebie do zadania musisz być zalogowany", HttpStatus.BAD_REQUEST);
        }


    }

    public ResponseEntity<String> deleteUserFromProduct(Long id) {
        Optional<Product> product = this.productRepository.findById(id);
        if (this.productRepository.existsById(id)) {
            if (product.get().getUser() != null) {
                if (this.loggedCheck.checkWhoIsLogged(product.get().getUser().getLogin())) {
                    this.productRepository.deleteUserFromProduct(id);
                    return new ResponseEntity<>("Użytkownik został usunięty", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Użytkownik przypisany do zadania może " +
                            "być usunięty tylko przez tego użytkownika.", HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity<>("Podany produkt nie posiada przypisanego użytkownika", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Nie istnieje produkt o podanym id", HttpStatus.BAD_REQUEST);


    }

    public ResponseEntity<String> delete(long id) {
        if(this.productRepository.existsById(id)){
            Product product = this.productRepository.findById(id);
            if (product.getUser() == null) {
                this.productRepository.delete(product);
                return new ResponseEntity<>("Produkt usunięty", HttpStatus.OK);
            } else if (this.loggedCheck.checkWhoIsLogged(product.getUser().getLogin())) {
                this.productRepository.delete(product);
                return new ResponseEntity<>("Zadanie usunięte", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Tylko użytkownik przypisany do produktu może je usunąć", HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>("Nie istnieje produkt o podanym id", HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<String> changeName(Long id, String name) {
        if(this.productRepository.existsById(id)){
            Product product = this.productRepository.findById(id).get();
            if (product.getUser() == null || this.loggedCheck.checkWhoIsLogged(product.getUser().getLogin())) {
                this.productRepository.changeName(id, name);
                return new ResponseEntity<>("Zmieniono " + product.getName() + " na " + name, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Zmienić nazwe może tylko użytkownik przypisany do produktu.", HttpStatus.OK);
            }
        }else{
            return new ResponseEntity<>("Nie istnieje produkt o podanym id", HttpStatus.BAD_REQUEST);
        }

    }
}
