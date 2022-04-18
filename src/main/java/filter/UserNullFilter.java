package filter;

import command.util.CommandHelper;
import entity.user.Role;
import filter.util.FilterHelper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static command.ConstantsCommand.*;
import static controllers.ConstantsJSP.ERROR_PAGE;

@WebFilter({APPLICATION_COM, PROFILE, BILLS, CLIENT, PENDING, ADMIN_COM, HELLO_ADMIN})
public class UserNullFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (FilterHelper.getUser(request) == null) {
            CommandHelper.errorRequestDispatcher((HttpServletRequest) request, (HttpServletResponse) response,
                    ERROR_PAGE, UserNullFilter.class.getSimpleName());
        }
        chain.doFilter(request, response);
    }
}
