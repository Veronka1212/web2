package exeption;

import javax.servlet.ServletException;
import java.io.IOException;

public class CommandException extends RuntimeException{
    private String command;

    public CommandException(ServletException e) {
    }

    public CommandException(IOException e) {
    }

    public String getCommand() {
        return command;
    }

    public String toString() {
        return command + getMessage();
    }
}
