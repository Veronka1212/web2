package filter;

import command.ConstantsCommand;
import dto.UserDTO;
import entity.user.Role;
import org.codehaus.plexus.component.annotations.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;
import java.io.IOException;

import static command.ConstantsCommand.*;

@WebFilter({APPLICATION_COM, PROFILE, BILLS})
public class ClientFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        UserDTO user = (UserDTO) session.getAttribute(USER);
        if (user.getRole().equals(Role.ADMIN)) {
            session.invalidate();
            request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
            return;
        }
        chain.doFilter(request, response);
    }
}
