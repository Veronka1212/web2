package filter;


import command.ConstantsCommand;
import command.ICommand;
import org.apache.http.HttpRequest;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static command.ConstantsCommand.*;
import static command.ConstantsCommand.COMMAND;
import static controllers.ConstantsJSP.ERROR_COM;

@WebFilter("/*")
public class CommandFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String command = request.getParameter(COMMAND);
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();
        if (command == null && !HOTEL.equals(path) && !BOOKING_PATH.equals(path)) {
            request.getRequestDispatcher(ERROR_COM).forward(request, response);
        }
        chain.doFilter(request, response);
    }
}
