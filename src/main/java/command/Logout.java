package command;

import command.util.ErrorHelper;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static command.ConstantsCommand.*;

@NoArgsConstructor
public class Logout implements ICommand {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().invalidate();
        ErrorHelper.errorSendRedirect(HOTEL, "logout", resp);
    }
}
