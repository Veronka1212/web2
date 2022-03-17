package command.util;

import dao.ApplicationDAOimpl;
import exeption.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorHelper {

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
}
