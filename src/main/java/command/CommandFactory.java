package command;

import javax.servlet.http.HttpServletRequest;

import static command.ConstantsCommand.*;

public class CommandFactory {

    public static ICommand getCommand(HttpServletRequest req) {
        String command = req.getParameter(COMMAND);
        ICommand iCommand = null;
        if (command != null) {
            try {
                command = command.toUpperCase();
                iCommand = CommandEnum.valueOf(command).getCommand();
            } catch (IllegalArgumentException e) {
                e.printStackTrace(); //filtr
            }
        }
        return iCommand;
    }
}
