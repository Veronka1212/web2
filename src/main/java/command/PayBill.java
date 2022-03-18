package command;

import command.util.CommandHelper;
import dao.ApplicationDAOimpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.BillService;
import service.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static command.ConstantsCommand.*;
import static command.ConstantsCommand.ID;

public class PayBill implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(ApplicationDAOimpl.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        BillService billService = new BillService();
        RoomService roomService = new RoomService();
        Integer id = Integer.valueOf(req.getParameter(ID));
        Integer room = Integer.valueOf(req.getParameter(ROOM));
        billService.pay(id);
        LOGGER.info("Bill paid");
        roomService.setCleaned(room);
        LOGGER.info("Room's status cleaned");
        CommandHelper.errorSendRedirect(BILL_PATH, "pay bill", resp);
    }
}
