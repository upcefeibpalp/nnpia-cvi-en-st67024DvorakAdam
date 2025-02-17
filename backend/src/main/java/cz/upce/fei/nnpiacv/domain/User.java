package cz.upce.fei.nnpiacv.domain;

import lombok.*;

@AllArgsConstructor
@Data
//@Getter
//@Setter
//@ToString
public class User {
    private long id;
    private String email;
    private String password;
}
