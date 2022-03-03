package command;

import dao.ApplicationDAOimpl;
import exeption.CommandException;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.BillService;
import service.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static command.ConstantsCommand.*;
import static command.ConstantsCommand.ID;

public class PayBill implements ICommand {

    private static final Logger logger = LogManager.getLogger(ApplicationDAOimpl.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        BillService billService = new BillService();
        RoomService roomService = new RoomService();
        Integer id = Integer.valueOf(req.getParameter(ID));
        Integer room = Integer.valueOf(req.getParameter(ROOM));
        billService.pay(id);
        roomService.setCleaned(room);
        try {
            resp.sendRedirect(BILL_PATH);
        } catch (IOException e) {
            logger.error("Error response send redirect in PayBill command");
            throw new CommandException(e);
        }
    }
}
