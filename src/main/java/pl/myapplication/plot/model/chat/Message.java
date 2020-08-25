package pl.myapplication.plot.model.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode (of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String message;
    @OneToOne
    @JoinColumn(name = "file_id")
    File file;

    public Message(String message) {
        this.message = message;
    }

    public Message(File file) {
        this.file = file;
    }

}
