package command;

import command.util.ErrorHelper;
import service.BillService;
import service.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static command.ConstantsCommand.*;
import static command.ConstantsCommand.ID;

public class PayBill implements ICommand {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        BillService billService = new BillService();
        RoomService roomService = new RoomService();
        Integer id = Integer.valueOf(req.getParameter(ID));
        Integer room = Integer.valueOf(req.getParameter(ROOM));
        billService.pay(id);
        roomService.setCleaned(room);
        ErrorHelper.errorSendRedirect(BILL_PATH, "pay bill", resp);
    }
}
