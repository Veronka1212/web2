package entity.user;

import lombok.*;

import java.util.Objects;

@Data
@Setter
@Builder
@EqualsAndHashCode(of = "id")
public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private Role role;

}
