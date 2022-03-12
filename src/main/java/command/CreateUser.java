package command;

import dao.ApplicationDAOimpl;
import dto.CreateUserDTO;
import entity.user.Role;
import exeption.CommandException;
import exeption.DaoException;
import exeption.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static command.ConstantsCommand.*;
import static command.ConstantsCommand.ERRORS;
import static controllers.ConstantsJSP.INDEX_PAGE;
import static controllers.ConstantsJSP.REGISTRATION_PAGE;

public class CreateUser implements ICommand {
    private static final Logger logger = LogManager.getLogger(ApplicationDAOimpl.class);

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
            getRequestDispatcher(INDEX_PAGE, req, resp);
        } catch (DaoException exception) {
            logger.error("DaoException in creat new user command");
            req.setAttribute(ERRORS_DAO, exception.getMessage());
            getRequestDispatcher(REGISTRATION_PAGE, req, resp);
        } catch (ValidationException e) {
            logger.error("Validation exception in creat new user command");
            req.setAttribute(ERRORS, e.getErrors());
            getRequestDispatcher(REGISTRATION_PAGE, req, resp);
        }
    }

    private void getRequestDispatcher(String page, HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher(page).forward(req, resp);
        } catch (ServletException e) {
            logger.error("ServletException in creat new user command");
            throw new CommandException(e);
        } catch (IOException e) {
            logger.error("IOException in creat new user command");
            throw new CommandException(e);
        }
    }
}
