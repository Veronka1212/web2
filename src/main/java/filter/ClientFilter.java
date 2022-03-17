package filter;

import entity.user.Role;
import filter.util.FilterHelper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import static command.ConstantsCommand.*;
import static command.ConstantsCommand.CLIENT;

@WebFilter({APPLICATION_COM, PROFILE, BILLS, CLIENT})
public class ClientFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        FilterHelper.mappingErrorPage(Role.ADMIN, FilterHelper.getUser(request),
                request, response, chain, ClientFilter.class.getSimpleName());
    }
}
