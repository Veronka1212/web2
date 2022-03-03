package command;

import dao.ApplicationDAOimpl;
import dto.UserDTO;
import exeption.CommandException;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ApplicationService;
import service.BillService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static command.ConstantsCommand.*;
import static controllers.ConstantsJSP.*;

public class Profile implements ICommand {

    private static final Logger logger = LogManager.getLogger(ApplicationDAOimpl.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        ApplicationService applicationService = new ApplicationService();
        BillService billService = new BillService();
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute(USER);
        String email = userDTO.getEmail();
        if (req.getRequestURI().equals(PROFILE_APP)) {
            req.setAttribute(APPLICATIONS, applicationService.findByEmail(email));
            getRequestDispatcher(req, resp, PROFILE_PAGE);
        }
        if (req.getRequestURI().equals(BILLS_PATH)) {
            req.setAttribute(USER_BILLS, billService.findByEmail(email));
            getRequestDispatcher(req, resp, BILL_PAGE);
        }

    }

    private void getRequestDispatcher(HttpServletRequest req, HttpServletResponse resp, String page) {
        try {
            req.getRequestDispatcher(page).forward(req, resp);
        } catch (ServletException e) {
            logger.error("Servlet Exception in profile command");
            throw new CommandException(e);
        } catch (IOException e) {
            logger.error("IOException in profile command");
            throw new CommandException(e);
        }
    }
}
