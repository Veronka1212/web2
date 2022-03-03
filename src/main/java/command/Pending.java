package command;

import lombok.SneakyThrows;
import service.ApplicationService;
import service.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static command.ConstantsCommand.*;
import static controllers.ConstantsJSP.*;

public class Pending implements ICommand {

    @SneakyThrows
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        final ApplicationService applicationService = new ApplicationService();
        final RoomService roomService = new RoomService();
        if (req.getMethod().equals(GET)) {
            String id = req.getParameter(ID);
            req.setAttribute(APPLICATION, applicationService.findById(id));
            req.setAttribute(ROOMS, roomService.findAll());
            req.setAttribute(FREE_ROOMS, roomService.findAllFreeById(id));
            req.getRequestDispatcher(PROCESSING_PAGE).forward(req, resp);
        }
    }
}

