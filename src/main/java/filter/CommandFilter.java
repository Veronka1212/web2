package filter;


import command.ICommand;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

import static command.ConstantsCommand.COMMAND;
import static controllers.ConstantsJSP.ERROR_COM;

@WebFilter("/*")
public class CommandFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String command = request.getParameter(COMMAND);
        if (command != null) {
            request.getRequestDispatcher(ERROR_COM).forward(request, response);
        }
        chain.doFilter(request, response);
    }
}
