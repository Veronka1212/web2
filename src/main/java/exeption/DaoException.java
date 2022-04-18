package exeption;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DaoException extends RuntimeException{
    private final Object error;

    public String getMessage() {
        return "DAO Exception!";
    }
}
