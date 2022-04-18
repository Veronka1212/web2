package command;

import command.util.CommandHelper;
import service.BillService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static command.ConstantsCommand.*;

public class DeleteBill implements ICommand {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        BillService billService = new BillService();
        Integer id = Integer.valueOf(req.getParameter(ID));
        billService.delete(id);
        CommandHelper.errorSendRedirect(BILL_PATH, "delete bill", resp);
    }
}
