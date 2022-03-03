package command;

import dao.ApplicationDAOimpl;
import dto.CreateApplicationDTO;
import dto.UserDTO;
import exeption.CommandException;
import exeption.ValidationException;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ApplicationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static command.ConstantsCommand.*;
import static controllers.ConstantsJSP.APPLICATION_PAGE;
import static controllers.ConstantsJSP.SUCCESSFUL_PAGE;

public class Booking implements ICommand {

    private static final Logger logger = LogManager.getLogger(ApplicationDAOimpl.class);

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
            getRequestDispatcher(SUCCESSFUL_PAGE, req, resp);
            logger.info("Booking done");
        } catch (ValidationException e) {
            logger.error("Validation Exception in Booking command");
            req.setAttribute(ERRORS, e.getErrors());
            getRequestDispatcher(APPLICATION_PAGE, req, resp);
        }
    }

    private void getRequestDispatcher(String page, HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher(page).forward(req, resp);
        } catch (ServletException e) {
            logger.error("ServletException in booking command");
            throw new CommandException(e);
        } catch (IOException e) {
            logger.error("IOException in booking command");
            throw new CommandException(e);
        }
    }
}

