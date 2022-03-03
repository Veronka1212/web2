package command;

import dao.ApplicationDAOimpl;
import entity.room.Bed;
import entity.room.Type;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static command.ConstantsCommand.*;
import static controllers.ConstantsJSP.*;

public class Application implements ICommand {
    private static final Logger logger = LogManager.getLogger(ApplicationDAOimpl.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(BEDS, Bed.values());
        req.setAttribute(TYPES, Type.values());
        try {
            req.getRequestDispatcher(APPLICATION_PAGE).forward(req, resp);
        } catch (ServletException e) {
            logger.error("ServletException");
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("IOException");
            e.printStackTrace();
        }
    }
}
