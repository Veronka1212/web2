package command;

import dto.UserDTO;
import lombok.SneakyThrows;
import service.ApplicationService;
import service.BillService;
import service.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static command.ConstantsCommand.*;
import static controllers.ConstantsJSP.*;

public class Profile implements ICommand {

    @SneakyThrows
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        ApplicationService applicationService = new ApplicationService();
        BillService billService = new BillService();
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute(USER);
        String email = userDTO.getEmail();
        if (req.getRequestURI().equals(PROFILE_APP)) {
            req.setAttribute(APPLICATIONS, applicationService.findByEmail(email));
            req.getRequestDispatcher(PROFILE_PAGE).forward(req, resp);
        }
        if (req.getRequestURI().equals(BILLS_PATH)) {
            req.setAttribute(USER_BILLS, billService.findByEmail(email));
            req.getRequestDispatcher(BILL_PAGE).forward(req, resp);
        }
    }
}
