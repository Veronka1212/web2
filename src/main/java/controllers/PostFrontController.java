package controllers;

import command.ConstantsCommand;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static command.ConstantsCommand.*;

@WebServlet(urlPatterns = {LOGIN, EVICT, BOOKING, LOGOUT, ADMIN_COM, CREATE_USER, LOCALE})
public class PostFrontController extends AbstractFrontController {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        processError(request, response
                , "method GET post is wrong for command!");
    }
}
