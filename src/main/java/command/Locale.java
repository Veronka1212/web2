package command;

import command.util.CommandHelper;
import dao.ApplicationDAOimpl;
import dto.UserDTO;
import entity.user.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static command.ConstantsCommand.*;

public class Locale implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(ApplicationDAOimpl.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        String lang = req.getParameter(LANGUAGE);
        req.getSession().setAttribute(LANGUAGE, lang);
        String page = req.getHeader("referer").replaceFirst("http://localhost:8081", "");
        try {
            UserDTO user = (UserDTO) req.getSession().getAttribute(USER);
            page = sendPage(user, page);
            CommandHelper.errorSendRedirect(page, LOCALE, resp);
            LOGGER.info("Locale changed successful");
        } catch (NullPointerException e) {
            LOGGER.error("No session, locale changed");
            CommandHelper.errorSendRedirect(page, LOCALE, resp);
        }
    }

    private String sendPage(UserDTO user, String page) {
        if (!page.contains(COMMAND) && user.getRole() == Role.USER) {
            page = CLIENT_PATH;
        }
        if (!page.contains(COMMAND) && user.getRole() == Role.ADMIN) {
            page = ADMIN_PATH;
        }
        return page;
    }
}