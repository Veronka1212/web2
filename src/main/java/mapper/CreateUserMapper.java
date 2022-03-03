package mapper;

import dto.CreateUserDTO;
import entity.user.Role;
import entity.user.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CreateUserMapper implements Mapperalable<CreateUserDTO, User> {

    @Override
    public User mapFrom(CreateUserDTO object) {
        return User.builder()
                .name(object.getName())
                .email(object.getEmail())
                .password(object.getPassword())
                .role(Role.valueOf(object.getRole()))
                .build();
    }
}
