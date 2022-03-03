package command;

import dao.ApplicationDAOimpl;
import dto.UserDTO;
import entity.user.Role;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import static command.ConstantsCommand.*;

public class Locale implements ICommand {

    private static final Logger logger = LogManager.getLogger(ApplicationDAOimpl.class);

    @SneakyThrows
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        String lang = req.getParameter(LANGUAGE);
        req.getSession().setAttribute(LANGUAGE, lang);
        String page = req.getHeader("referer").replaceFirst("http://localhost:8081", "");
        try {
            UserDTO user = (UserDTO) req.getSession().getAttribute(USER);
            resp.sendRedirect(sendPage(user, page));
            logger.info("Locale changed successful");
        } catch (NullPointerException e) {
            logger.error("No session, locale changed");
            resp.sendRedirect(page);
        }
    }

    private String sendPage(UserDTO user, String page) {
        if (!page.contains(COMMAND) && user.getRole() == Role.USER) {
            page += COMMAND_USER;
        }
        if (!page.contains(COMMAND) && user.getRole() == Role.ADMIN) {
            page += COMMAND_ADMIN;
        }
        return page;
    }
}