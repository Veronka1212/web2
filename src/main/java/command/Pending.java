package command;

import dao.ApplicationDAOimpl;
import exeption.CommandException;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ApplicationService;
import service.RoomService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static command.ConstantsCommand.*;
import static controllers.ConstantsJSP.*;

public class Pending implements ICommand {

    private static final Logger logger = LogManager.getLogger(ApplicationDAOimpl.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        final ApplicationService applicationService = new ApplicationService();
        final RoomService roomService = new RoomService();
        String id = req.getParameter(ID);
        req.setAttribute(APPLICATION, applicationService.findById(id));
        req.setAttribute(ROOMS, roomService.findAll());
        req.setAttribute(FREE_ROOMS, roomService.findAllFreeById(id));
        try {
            req.getRequestDispatcher(PROCESSING_PAGE).forward(req, resp);
        } catch (ServletException e) {
            logger.error("Servlet exception in pending command");
            throw new CommandException(e);
        } catch (IOException e) {
            logger.error("IOException in pending command");
            throw new CommandException(e);
        }
    }
}

