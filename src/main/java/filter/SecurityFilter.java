package filter;//package filter;
//
//import dto.UserDTO;
//import entity.user.Role;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@WebFilter("/re")
//public class SecurityFilter implements Filter {
//    public static final List<String> URLS = Arrays.asList("/application", "/login", "/home", "/registration", "/index", "/");
//    public static final List<String> ADMIN_URLS = Arrays.asList("/regapplication");
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String currentPage = ((HttpServletRequest) request).getRequestURI();
//        UserDTO userDTO = (UserDTO) ((HttpServletRequest) request).getSession().getAttribute("user");
//        if ((isPublicUrl(currentPage) || userSessionOn(userDTO)) || (isAdminUrl(currentPage) || adminSessionOn(userDTO))) {
//            chain.doFilter(request, response);
//        } else {
//          String startPage = ((HttpServletRequest) request).getHeader("referrer");
//          ((HttpServletResponse) response).sendRedirect(startPage != null ? startPage : "/WEB-INF/error404.jsp");
//        }
//
//    }
//
//    private boolean userSessionOn(UserDTO userDTO) {
//        return userDTO != null && userDTO.getRole() == Role.USER;
//    }
//
//    private boolean adminSessionOn(UserDTO userDTO) {
//        return userDTO != null && userDTO.getRole() == Role.ADMIN;
//    }
//
//    private boolean isPublicUrl(String url) {
//        return URLS.contains(getUrl(url));
//    }
//
//    private boolean isAdminUrl(String url) {
//        return ADMIN_URLS.contains(getUrl(url));
//    }
//
//    private String getUrl(String url) {
//        Pattern pattern = Pattern.compile("[/]{1}[a-z]*");
//        Matcher matcher = pattern.matcher(url);
//        matcher.find();
//        return matcher.group();
//    }
//
//}
