package filter;

import entity.user.Role;
import filter.util.FilterHelper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;

import static command.ConstantsCommand.*;

@WebFilter({PENDING, ADMIN_COM, HELLO_ADMIN})
public class AdminFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        FilterHelper.mappingErrorPage(Role.USER, FilterHelper.getUser(request),
                request, response, chain, ClientFilter.class.getSimpleName());
    }
}
