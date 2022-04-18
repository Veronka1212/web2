package filter.util;

import command.util.CommandHelper;
import dao.ApplicationDAOimpl;
import dto.UserDTO;
import entity.user.Role;
import exeption.FilterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static command.ConstantsCommand.ROLE;
import static command.ConstantsCommand.USER;
import static controllers.ConstantsJSP.ERROR_PAGE;

public class FilterHelper {

    private static final Logger LOGGER = LogManager.getLogger(ApplicationDAOimpl.class);

    public static void mappingErrorPage(Role role, UserDTO user,
                                        ServletRequest request, ServletResponse response, FilterChain chain, String filterName) {
        if (user != null && user.getRole().equals(role)) {
            request.setAttribute(ROLE, user.getRole().toString());
            CommandHelper.errorRequestDispatcher((HttpServletRequest) request, (HttpServletResponse) response, ERROR_PAGE, role.toString() + "filter");
        }
        try {
            chain.doFilter(request, response);
        } catch (IOException e) {
            LOGGER.error("IOException in " + filterName + " filter!");
            throw new FilterException(e);
        } catch (ServletException e) {
            LOGGER.error("ServletException in " + filterName + " filter!");
            throw new FilterException(e);
        }
    }

    public static UserDTO getUser(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        return (UserDTO) session.getAttribute(USER);
    }
}
