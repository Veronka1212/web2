package dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserDTO {
    String id;
    String name;
    String email;
    String password;
    String role;
}
