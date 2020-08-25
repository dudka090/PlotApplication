package pl.myapplication.plot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode (of = "id")
public class Product implements Serializable {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    public Product(String name) {
        this.name = name;
    }
}
