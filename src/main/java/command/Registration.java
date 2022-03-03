package command;

import controllers.ConstantsJSP;
import dto.CreateUserDTO;
import entity.user.Role;
import exeption.DaoException;
import exeption.ValidationException;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static command.ConstantsCommand.*;
import static controllers.ConstantsJSP.*;

@NoArgsConstructor
public class Registration implements ICommand {

    @SneakyThrows
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        UserService userService = new UserService();
        if (req.getMethod().equals(GET)) {
            req.getRequestDispatcher(REGISTRATION_PAGE).forward(req, resp);
        } else {
            resp.setContentType(CONTENT_TYPE);
            CreateUserDTO createUserDTO = CreateUserDTO.builder()
                    .name(req.getParameter(NAME))
                    .email(req.getParameter(EMAIL))
                    .password(req.getParameter(PASSWORD))
                    .role(Role.USER.name())
                    .build();
            try {
                userService.create(createUserDTO);
                req.getRequestDispatcher(INDEX_PAGE).forward(req, resp);
            } catch (DaoException exception) {
                req.setAttribute(ERRORS_DAO, exception.getMessage());
                req.getRequestDispatcher(REGISTRATION_PAGE).forward(req, resp);
            } catch (ValidationException e) {
                req.setAttribute(ERRORS, e.getErrors());
                req.getRequestDispatcher(REGISTRATION_PAGE).forward(req, resp);
            }
        }
    }
}
