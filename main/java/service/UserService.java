package service;

import dao.UserDAOimpl;
import dto.CreateUserDTO;
import dto.UserDTO;
import entity.user.User;
import exeption.DaoException;
import exeption.ValidationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import mapper.CreateUserMapper;
import mapper.UserMapper;
import validator.CreateUserValidation;
import validator.Validator;

import java.sql.SQLException;
import java.util.Optional;

@NoArgsConstructor
public class UserService {

    public final UserDAOimpl userDAO = new UserDAOimpl();
    private final UserMapper userMapper = new UserMapper();
    private final CreateUserMapper createUserMapper = new CreateUserMapper();
    private final CreateUserValidation createUserValidation = new CreateUserValidation();

    public Integer create(CreateUserDTO createUserDTO) {
        Validator validator = createUserValidation.resultOfValidation(createUserDTO);
        if (!validator.resultOfValidation()) {
            throw new ValidationException(validator.getErrors());
        }
        User user = createUserMapper.mapFrom(createUserDTO);
        userDAO.save(user);
        return user.getId();
    }

    public Optional<UserDTO> login(String email, String password) {
        return userDAO.findByEmailAndPass(email, password)
                .map(userMapper::getFrom);
    }
}
