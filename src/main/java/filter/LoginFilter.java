package filter;

import command.ConstantsCommand;
import controllers.ConstantsJSP;
import dto.UserDTO;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static command.ConstantsCommand.*;
import static command.ConstantsCommand.HOTEL;


@WebFilter("/*")
public class LoginFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();
        if (HOTEL.equals(path) || REG_PATH.equals(path) || LOGIN_PATH.equals(path)) {
            chain.doFilter(request, response);
        } else {
            HttpSession session = httpRequest.getSession();
            UserDTO user = (UserDTO) session.getAttribute(USER);
            if (user == null) {
                session.invalidate();
                request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
                return;
            }
            chain.doFilter(request, response);
        }
    }
}
