package exeption;

public class NegativeArgumentException extends IllegalArgumentException {
    private String byn;

    public NegativeArgumentException() {
        super();
    }

    public NegativeArgumentException(String s, String byn) {
        super(s);
        this.byn = byn;
    }

    public String getByn() {
        return byn;
    }

    public String toString() {
        return byn + getMessage();
    }
}
