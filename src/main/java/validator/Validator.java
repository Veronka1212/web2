package validator;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Validator{

    private List<Error> errors = new ArrayList<>();

    public void addError(Error error) {
        this.errors.add(error);
    }

    public boolean resultOfValidation() {
        return errors.isEmpty();
    }
}
