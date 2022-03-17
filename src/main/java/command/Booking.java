package command;

import command.util.ErrorHelper;
import dao.ApplicationDAOimpl;
import dto.CreateApplicationDTO;
import dto.UserDTO;
import exeption.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ApplicationService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static command.ConstantsCommand.*;
import static controllers.ConstantsJSP.APPLICATION_PAGE;
import static controllers.ConstantsJSP.SUCCESSFUL_PAGE;

public class Booking implements ICommand {

    private static final Logger LOGGER = LogManager.getLogger(ApplicationDAOimpl.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApplicationService applicationService = new ApplicationService();
        UserDTO user = (UserDTO) req.getSession().getAttribute(USER);

        CreateApplicationDTO applicationDTO = CreateApplicationDTO.builder()
                .email(user.getEmail())
                .bed(req.getParameter(BED))
                .type(req.getParameter(TYPE))
                .time(req.getParameter(TIME))
                .status(ZERO.toString())
                .processing_status(ZERO.toString())
                .build();
        try {
            applicationService.create(applicationDTO);
            ErrorHelper.errorRequestDispatcher(req, resp, SUCCESSFUL_PAGE, BOOKING);
            LOGGER.info("Booking done");
        } catch (ValidationException e) {
            LOGGER.error("Validation Exception in Booking command");
            req.setAttribute(ERRORS, e.getErrors());
            ErrorHelper.errorRequestDispatcher(req, resp, APPLICATION_PAGE, BOOKING);
        }
    }
}

