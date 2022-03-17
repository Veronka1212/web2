package command;

import command.util.ErrorHelper;
import dto.UserDTO;
import service.BillService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static command.ConstantsCommand.*;
import static controllers.ConstantsJSP.BILL_PAGE;

public class Bills implements ICommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {

        BillService billService = new BillService();
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute(USER);
        String email = userDTO.getEmail();
        req.setAttribute(USER_BILLS, billService.findByEmail(email));
        ErrorHelper.errorRequestDispatcher(req, resp, BILL_PAGE, BILLS);
    }
}
