package command;

import command.util.CommandHelper;
import service.ApplicationService;
import service.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static command.ConstantsCommand.*;
import static controllers.ConstantsJSP.*;

public class Pending implements ICommand {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        final ApplicationService applicationService = new ApplicationService();
        final RoomService roomService = new RoomService();
        String id = req.getParameter(ID);
        req.setAttribute(APPLICATION, applicationService.findById(id));
        req.setAttribute(ROOMS, roomService.findAll());
        req.setAttribute(FREE_ROOMS, roomService.findAllFreeById(id));
        CommandHelper.errorRequestDispatcher(req, resp, PROCESSING_PAGE, PENDING);
    }
}

