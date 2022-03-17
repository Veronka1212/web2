package command;

import command.util.CommandHelper;
import dto.ApplicationDTO;
import service.ApplicationService;
import service.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static command.ConstantsCommand.*;
import static controllers.ConstantsJSP.*;

public class Pending implements ICommand {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        final ApplicationService applicationService = new ApplicationService();
        final RoomService roomService = new RoomService();
        String id = req.getParameter(ID);
        Optional<ApplicationDTO> application = applicationService.findById(id);
        application.ifPresent(applicationDTO -> req.setAttribute(APPLICATION, applicationDTO));
        req.setAttribute(ROOMS, roomService.findAll());
        req.setAttribute(FREE_ROOMS, roomService.findAllFreeById(id));
        CommandHelper.errorRequestDispatcher(req, resp, PROCESSING_PAGE, PENDING);
    }
}

