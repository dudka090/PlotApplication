package pl.myapplication.plot.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(schema = "public")
@Data
@EqualsAndHashCode
@NoArgsConstructor

public class User implements Serializable {
    @Id
    String login;
    String password;
    String email;
    String displayName;
    @OneToMany (orphanRemoval = true)
    List<Reservation> reservations;
    @OneToMany (orphanRemoval = true)
    List<Product> products;
    @OneToMany (orphanRemoval = true)
    List<ToDo> toDoList;

    public User(String login, String password, String email, String displayName) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.displayName = displayName;
    }
}
