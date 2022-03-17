package command;

import command.util.CommandHelper;
import dao.ApplicationDAOimpl;
import dto.CreateUserDTO;
import entity.user.Role;
import exeption.DaoException;
import exeption.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static command.ConstantsCommand.*;
import static command.ConstantsCommand.ERRORS;
import static controllers.ConstantsJSP.*;

public class CreateUser implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(ApplicationDAOimpl.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserService userService = new UserService();
        resp.setContentType(CONTENT_TYPE);
        CreateUserDTO createUserDTO = CreateUserDTO.builder()
                .name(req.getParameter(NAME))
                .email(req.getParameter(EMAIL))
                .password(req.getParameter(PASSWORD))
                .role(Role.USER.name())
                .build();
        try {
            userService.create(createUserDTO);
            CommandHelper.errorRequestDispatcher(req, resp, INDEX_PAGE, USER);
        } catch (DaoException exception) {
            LOGGER.error("DaoException in creat new user command");
            req.setAttribute(ERRORS_DAO, exception.getMessage());
            CommandHelper.errorRequestDispatcher(req, resp, REGISTRATION_PAGE, USER);
        } catch (ValidationException e) {
            LOGGER.error("Validation exception in creat new user command");
            req.setAttribute(ERRORS, e.getErrors());
            CommandHelper.errorRequestDispatcher(req, resp, REGISTRATION_PAGE, USER);
        }
    }
}
