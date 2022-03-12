package command;

import dao.CheckoutDAOimpl;
import service.ApplicationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloAdmin implements ICommand {
    final ApplicationService applicationService = new ApplicationService();
    final CheckoutDAOimpl checkoutDAOimpl = new CheckoutDAOimpl();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Login.returnToAdminPage(req, resp, applicationService, checkoutDAOimpl);
    }
}
