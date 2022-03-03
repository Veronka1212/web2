package command;

import dao.ApplicationDAOimpl;
import dao.CheckoutDAOimpl;
import dto.UserDTO;
import entity.user.Role;
import exeption.CommandException;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ApplicationService;
import service.RoomService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static command.ConstantsCommand.*;
import static controllers.ConstantsJSP.*;

@NoArgsConstructor
public class Login implements ICommand {

    private static final Logger logger = LogManager.getLogger(ApplicationDAOimpl.class);

    @SneakyThrows
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        final UserService userService = new UserService();
        final RoomService roomService = new RoomService();
        if (req.getMethod().equals(GET)) {
            UserDTO userDTO = (UserDTO) req.getSession().getAttribute(USER);
            req.setAttribute("myRooms", roomService.findClientRoom(userDTO.getEmail()));
            req.getRequestDispatcher(CLIENT).forward(req, resp);
        } else {
            Optional<UserDTO> userDTO = userService
                    .login(
                            req.getParameter(EMAIL),
                            req.getParameter(PASSWORD)
                    );
            if (userDTO.isPresent()) {
                mappingByRole(userDTO.get(), req, resp);
                logger.info("Login done");
            } else {
                logger.error("Invalid login!");
                resp.sendRedirect(ERROR_MESSAGE + req.getParameter(EMAIL));
            }
        }
    }

    @SneakyThrows
    private void mappingByRole(UserDTO userDTO, HttpServletRequest req, HttpServletResponse resp) {
        final RoomService roomService = new RoomService();
        final ApplicationService applicationService = new ApplicationService();
        final CheckoutDAOimpl checkoutDAOimpl = new CheckoutDAOimpl();
        req.getSession().setAttribute(USER, userDTO);
        if (userDTO.getRole().equals(Role.USER)) {
            req.setAttribute("myRooms", roomService.findClientRoom(userDTO.getEmail()));
            logger.info("User login");
            req.getRequestDispatcher(CLIENT).forward(req, resp);
        } else {
            returnToAdminPage(req, resp, applicationService, checkoutDAOimpl);
        }
    }


    public static void returnToAdminPage(HttpServletRequest req, HttpServletResponse resp, ApplicationService applicationService, CheckoutDAOimpl checkoutDAOimpl) {
        req.setAttribute("checkouts", checkoutDAOimpl.findAll());
        req.setAttribute(APPLICATIONS, applicationService.findAllPending());
        try {
            req.getRequestDispatcher(ADMIN_PAGE).forward(req, resp);
            logger.info("Admin login");
        } catch (ServletException e) {
            logger.error("Servlet exception in admin login command");
            throw new CommandException(e);
        } catch (IOException e) {
            logger.error("IOException in admin login command");
            throw new CommandException(e);
        }
    }
}
