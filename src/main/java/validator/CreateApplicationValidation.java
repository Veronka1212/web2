package validator;

import dao.ApplicationDAOimpl;
import dto.CreateApplicationDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor
public class CreateApplicationValidation implements Validailiable<CreateApplicationDTO> {

    private static final Logger logger = LogManager.getLogger(ApplicationDAOimpl.class);

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    public Validator resultOfValidation(CreateApplicationDTO object) {
        Validator validator = new Validator();
        if (!validate(object.getEmail())) {
            validator.addError(Error.of("error.email", "Incorrect email"));
            logger.error("Validation error!");
        }
        return validator;
    }

    public static boolean validate(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }
}
