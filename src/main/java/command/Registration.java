package command;

import command.util.CommandHelper;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static command.ConstantsCommand.*;
import static controllers.ConstantsJSP.*;

@NoArgsConstructor
public class Registration implements ICommand {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {
        CommandHelper.errorRequestDispatcher(req, resp, REGISTRATION_PAGE, REGISTRATION);
    }
}
