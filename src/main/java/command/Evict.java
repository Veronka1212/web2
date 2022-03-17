package command;

import command.util.ErrorHelper;
import dao.ApplicationDAOimpl;
import exeption.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.CheckoutService;
import service.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static command.ConstantsCommand.*;

public class Evict implements ICommand {
    private static final Logger LOGGER = LogManager.getLogger(ApplicationDAOimpl.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        final CheckoutService checkoutService = new CheckoutService();
        final RoomService roomService = new RoomService();

        Integer id = Integer.valueOf(req.getParameter(ID));
        Integer room = Integer.valueOf(req.getParameter(ROOM));
        roomService.setFree(room);
        checkoutService.delete(id);
        LOGGER.info("Checkout approved and deleted");
        ErrorHelper.errorSendRedirect(ADMIN_PATH, EVICT, resp);
    }
}
