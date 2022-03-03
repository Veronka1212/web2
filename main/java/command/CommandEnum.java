package command;

public enum CommandEnum {
    LOGIN(new Login()),
    LOGOUT(new Logout()),
    LOCALE(new Locale()),
    REGISTRATION(new Registration()),
    PENDING(new Pending()),
    APPLICATION(new Application()),
    PROFILE(new Profile()),
    ADMIN(new Admin()),
    PAY(new PayBill()),
    BOOKING(new Booking()),
    DELETEBILL(new DeleteBill()),
    DELETEAPPLICATION(new DeleteApplication()),
    CHECKOUT(new Checkout());

    private ICommand command;

    CommandEnum(ICommand command) {
        this.command = command;
    }

    public ICommand getCommand() {
        return command;
    }
}
