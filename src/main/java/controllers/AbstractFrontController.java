package controllers;

import command.CommandFactory;
import command.ICommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractFrontController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        handleRequest(request, response);
    }

    protected void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ICommand iCommand = CommandFactory.getCommand(req);
        iCommand.execute(req, resp);
    }

    protected void processError(HttpServletRequest request, HttpServletResponse response, String msg)
            throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, msg);
    }
}


