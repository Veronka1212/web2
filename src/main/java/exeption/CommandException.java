package exeption;

import javax.servlet.ServletException;
import java.io.IOException;

public class CommandException extends RuntimeException{
    private String command;

    public CommandException() {
        super();
    }

    public CommandException(String s, String exception) {
        super(s);
        this.command = exception;
    }

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
