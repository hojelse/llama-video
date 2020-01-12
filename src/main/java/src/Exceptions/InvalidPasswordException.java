package src.Exceptions;

public class InvalidPasswordException  extends Exception{

    public InvalidPasswordException(){
        super("Password is incorrect!");
    }

}
