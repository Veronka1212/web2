package exeption;

public class ServiceException extends RuntimeException{
    private String exception;

    public ServiceException() {
        super();
    }

    public ServiceException(String s, String exception) {
        super(s);
        this.exception = exception;
    }

    public ServiceException(DaoException e) {
    }

    public String getException() {
        return exception;
    }

    public String toString() {
        return exception + getMessage();
    }
}
