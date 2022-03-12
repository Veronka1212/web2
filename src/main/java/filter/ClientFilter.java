package filter;

import command.ConstantsCommand;
import controllers.ConstantsJSP;
import dto.UserDTO;
import entity.user.Role;
import org.codehaus.plexus.component.annotations.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;
import java.io.IOException;

import static command.ConstantsCommand.*;
import static controllers.ConstantsJSP.*;

@WebFilter({APPLICATION_COM, PROFILE, BILLS})
public class ClientFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        UserDTO user = (UserDTO) session.getAttribute(USER);
        if (user==null || user.getRole().equals(Role.ADMIN)) {
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
        }
        chain.doFilter(request, response);
    }
}
