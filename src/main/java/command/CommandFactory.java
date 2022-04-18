package command;

import javax.servlet.http.HttpServletRequest;

import static command.ConstantsCommand.*;

public class CommandFactory {

    public static ICommand getCommand(HttpServletRequest req) {
        String command = req.getParameter(COMMAND);
        command = command.toUpperCase();
        return CommandEnum.valueOf(command).getCommand();
    }
}
