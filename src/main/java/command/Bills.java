package command;

import dao.ApplicationDAOimpl;
import dto.UserDTO;
import exeption.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ApplicationService;
import service.BillService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static command.ConstantsCommand.USER;
import static command.ConstantsCommand.USER_BILLS;
import static controllers.ConstantsJSP.BILL_PAGE;

public class Bills implements  ICommand{
    private static final Logger logger = LogManager.getLogger(ApplicationDAOimpl.class);
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        BillService billService = new BillService();
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute(USER);
        String email = userDTO.getEmail();
        req.setAttribute(USER_BILLS, billService.findByEmail(email));
        getRequestDispatcher(req, resp, BILL_PAGE);
    }

    private void getRequestDispatcher(HttpServletRequest req, HttpServletResponse resp, String page) {
        try {
            req.getRequestDispatcher(page).forward(req, resp);
        } catch (ServletException e) {
            logger.error("Servlet Exception in bills command");
            throw new CommandException(e);
        } catch (IOException e) {
            logger.error("IOException in bills command");
            throw new CommandException(e);
        }
    }
}
