package controllers;

import command.ConstantsCommand;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static command.ConstantsCommand.*;

@WebServlet(urlPatterns = {BILLS, REGISTRATION, PENDING, CLIENT, PROFILE, APPLICATION_COM, CHECKOUT, HELLO_ADMIN})
public class GetFrontController extends AbstractFrontController {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        processError(request, response
                , "method POST is wrong for command!");
    }

}
