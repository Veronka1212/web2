package command;

import command.util.ErrorHelper;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static command.ConstantsCommand.*;
import static controllers.ConstantsJSP.*;

@NoArgsConstructor
public class Registration implements ICommand {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        ErrorHelper.errorRequestDispatcher(req, resp, REGISTRATION_PAGE, REGISTRATION);
    }
}
