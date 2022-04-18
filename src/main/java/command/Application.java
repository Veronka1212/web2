package command;

import command.util.CommandHelper;
import entity.room.Bed;
import entity.room.Type;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static command.ConstantsCommand.*;
import static controllers.ConstantsJSP.*;

public class Application implements ICommand {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(BEDS, Bed.values());
        req.setAttribute(TYPES, Type.values());
        CommandHelper.errorRequestDispatcher(req, resp, APPLICATION_PAGE, APPLICATION);
    }
}
