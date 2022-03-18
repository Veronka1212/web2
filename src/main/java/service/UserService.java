package service;

import dao.ApplicationDAOimpl;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import validator.CreateUserValidation;
import validator.Validator;

import java.sql.SQLException;
import java.util.Optional;

@NoArgsConstructor
public class UserService {

    private static final Logger LOGGER = LogManager.getLogger(ApplicationDAOimpl.class);

    public final UserDAOimpl userDAO = new UserDAOimpl();
    private final UserMapper userMapper = new UserMapper();
    private final CreateUserMapper createUserMapper = new CreateUserMapper();
    private final CreateUserValidation createUserValidation = new CreateUserValidation();

    public Integer create(CreateUserDTO createUserDTO) {
        Validator validator = createUserValidation.resultOfValidation(createUserDTO);
        if (!validator.resultOfValidation()) {
            LOGGER.error("Error validation! Can't create new user!");
            throw new ValidationException(validator.getErrors());
        }
        User user = createUserMapper.mapFrom(createUserDTO);
        return userDAO.save(user);
    }

    public Optional<UserDTO> login(String email, String password) {
        return userDAO.findByEmailAndPass(email, password)
                .map(userMapper::getFrom);
    }

    public void delete(Integer id) {
        userDAO.delete(id);
    }

    public Optional<UserDTO> findById(Integer id) {
        return userDAO.findById(id)
                .map(userMapper::getFrom);
    }
}
