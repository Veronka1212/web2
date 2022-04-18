package command.util;

import dao.ApplicationDAOimpl;
import dao.CheckoutDAOimpl;
import exeption.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ApplicationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static command.ConstantsCommand.APPLICATIONS;
import static controllers.ConstantsJSP.ADMIN_PAGE;

public class CommandHelper {

    private static final Logger LOGGER = LogManager.getLogger(ApplicationDAOimpl.class);

    public static void errorRequestDispatcher(HttpServletRequest req, HttpServletResponse resp, String page, String command) {
        try {
            req.getRequestDispatcher(page).forward(req, resp);
        } catch (ServletException e) {
            LOGGER.error("ServletException in " + command + " command");
            throw new CommandException(e);
        } catch (IOException e) {
            LOGGER.error("IOException in " + command + " command");
            throw new CommandException(e);
        }
    }

    public static void errorSendRedirect(String path, String command, HttpServletResponse resp) {
        try {
            resp.sendRedirect(path);
        } catch (IOException e) {
            LOGGER.error("IOException in " + command + " command");
            throw new CommandException(e);
        }
    }

    public static void returnToAdminPage(HttpServletRequest req, HttpServletResponse resp, ApplicationService applicationService, CheckoutDAOimpl checkoutDAOimpl) {
        req.setAttribute("checkouts", checkoutDAOimpl.findAll());
        req.setAttribute(APPLICATIONS, applicationService.findAllPending());
        CommandHelper.errorRequestDispatcher(req, resp, ADMIN_PAGE, "admin login");
    }
}
