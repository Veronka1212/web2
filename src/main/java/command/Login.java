package command;

import command.util.CommandHelper;
import dao.ApplicationDAOimpl;
import dao.CheckoutDAOimpl;
import dto.UserDTO;
import entity.user.Role;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ApplicationService;
import service.RoomService;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static command.ConstantsCommand.*;
import static controllers.ConstantsJSP.*;
import static controllers.ConstantsJSP.CLIENT;

@NoArgsConstructor
public class Login implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(ApplicationDAOimpl.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        final UserService userService = new UserService();
        Optional<UserDTO> userDTO = userService
                .login(
                        req.getParameter(EMAIL),
                        req.getParameter(PASSWORD)
                );
        if (userDTO.isPresent()) {
            mappingByRole(userDTO.get(), req, resp);
            LOGGER.info("Login done");
        } else {
            LOGGER.error("Invalid login!");
            CommandHelper.errorSendRedirect(ERROR_MESSAGE + req.getParameter(EMAIL), "login", resp);
        }
    }

    private void mappingByRole(UserDTO userDTO, HttpServletRequest req, HttpServletResponse resp) {
        final RoomService roomService = new RoomService();
        final ApplicationService applicationService = new ApplicationService();
        final CheckoutDAOimpl checkoutDAOimpl = new CheckoutDAOimpl();
        req.getSession().setAttribute(USER, userDTO);
        if (userDTO.getRole().equals(Role.USER)) {
            req.setAttribute("name", userDTO.getName());
            req.setAttribute("myRooms", roomService.findClientRoom(userDTO.getEmail()));
            LOGGER.info("User login");
            CommandHelper.errorRequestDispatcher(req, resp, CLIENT, "user login");
        } else {
            CommandHelper.returnToAdminPage(req, resp, applicationService, checkoutDAOimpl);
        }
    }
}
