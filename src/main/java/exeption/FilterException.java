package exeption;

import javax.servlet.ServletException;
import java.io.IOException;

public class FilterException extends RuntimeException{
    private String filter;

    public FilterException(ServletException e) {
    }

    public FilterException(IOException e) {
    }

    public String getCommand() {
        return filter;
    }

    public String toString() {
        return filter + getMessage();
    }
}
