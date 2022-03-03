package command;

import dao.ApplicationDAOimpl;
import exeption.CommandException;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static command.ConstantsCommand.*;

@NoArgsConstructor
public class Logout implements ICommand {

    private static final Logger logger = LogManager.getLogger(ApplicationDAOimpl.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().invalidate();
        try {
            resp.sendRedirect(HOTEL);
        } catch (IOException e) {
            logger.error("Error response send redirect in Login command");
            throw new CommandException(e);
        }
    }
}
