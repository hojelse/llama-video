package src.Exceptions;

public class UsernameTakenException extends Exception{
    private String username;

    public UsernameTakenException(String username) {
        super("Username " + username + " already taken!");
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
