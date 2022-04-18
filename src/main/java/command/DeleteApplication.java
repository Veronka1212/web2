package command;

import dao.ApplicationDAOimpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ApplicationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static command.ConstantsCommand.*;

public class DeleteApplication implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(ApplicationDAOimpl.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApplicationService applicationService = new ApplicationService();
        String id = req.getParameter(ID);
        if (id != null) {
            applicationService.delete(Integer.parseInt(id));
            LOGGER.info("Application deleted");
            resp.sendRedirect(PROFILE_PATH);
        }
    }
}
