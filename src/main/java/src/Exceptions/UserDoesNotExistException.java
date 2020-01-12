package src.Exceptions;

public class UserDoesNotExistException extends Exception{

    public UserDoesNotExistException(String username){
        super("The user '" + username + "' does not exist. Click create account to make an account");
    }


}
