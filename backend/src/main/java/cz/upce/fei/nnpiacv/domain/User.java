package cz.upce.fei.nnpiacv.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@Data
//@Getter
//@Setter
//@ToString
@Entity
@NoArgsConstructor
@Table(name = "app_user")
public class User {
    @Id
    private long id;
    @Column(unique = true)
    private String email;
    private String password;

    public Long getId() {
        return id;
    }
}
