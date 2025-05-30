package cz.upce.fei.nnpiacv.domain;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Data
//@Getter
//@Setter
//@ToString
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "app_user")
public class User {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String email;
    private String password;
    private boolean active;

    public Long getId() {
        return id;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.active = true;
    }
    public User(String email, String password, boolean active) {
        this.email = email;
        this.password = password;
        this.active = active;
    }

}
