package validator;

import dao.ApplicationDAOimpl;
import dto.CreateUserDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor
public class CreateUserValidation implements Validailiable<CreateUserDTO> {

    private static final Logger logger = LogManager.getLogger(ApplicationDAOimpl.class);

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    public Validator resultOfValidation(CreateUserDTO object) {
        Validator validator = new Validator();

        if (!validate(object.getEmail())) {
            validator.addError(Error.of("error.email", "Incorrect email"));
            logger.error("Error e-mail!");
        }
        if (object.getPassword().length() < 8) {
            validator.addError(Error.of("error.password", "You password must be longer"));
            logger.error("Error password!");
        }
        return validator;
    }

    public static boolean validate(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }
}