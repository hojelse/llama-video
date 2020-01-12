package src.Exceptions;

public class InvalidLoginException extends Exception {


    public InvalidLoginException(){
        super("Username doesnt match the password, try again or create an account");
    }


}
