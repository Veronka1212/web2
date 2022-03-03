package command;

import lombok.SneakyThrows;
import service.BillService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static command.ConstantsCommand.*;

public class DeleteBill implements ICommand {

    @SneakyThrows
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        BillService billService = new BillService();
        Integer id = Integer.valueOf(req.getParameter(ID));
        billService.delete(id);
        resp.sendRedirect(BILL_PATH);
    }
}
