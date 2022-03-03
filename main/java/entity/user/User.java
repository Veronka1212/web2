package entity.user;

import lombok.*;

import java.util.Objects;

@Data
@Setter
@Builder
public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
