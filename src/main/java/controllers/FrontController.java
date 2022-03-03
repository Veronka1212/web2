package controllers;

import command.CommandFactory;
import command.ICommand;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static command.ConstantsCommand.*;

@WebServlet(urlPatterns = {HOTEL_COM, ADMIN_COM, CHECKOUT, LOGIN, LOGOUT, LOCALE, REGISTRATION, APPLICATION_COM, PROFILE, BILLS, PENDING})
public class FrontController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        handleRequest(req, resp);
    }

    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ICommand iCommand = CommandFactory.getCommand(req);
        iCommand.execute(req, resp);
    }
}
