package command;

import command.util.ErrorHelper;
import dto.UserDTO;
import service.ApplicationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static command.ConstantsCommand.*;
import static controllers.ConstantsJSP.*;

public class Profile implements ICommand {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        ApplicationService applicationService = new ApplicationService();
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute(USER);
        String email = userDTO.getEmail();
        req.setAttribute(APPLICATIONS, applicationService.findByEmail(email));
        ErrorHelper.errorRequestDispatcher(req, resp, PROFILE_PAGE, PROFILE);
    }
}
