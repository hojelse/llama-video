package src.Exceptions;

public class InvalidSignupCredentials extends Exception {

    public InvalidSignupCredentials(){
        super("The username must be atleast 3 characters long and must only contain leters from a-z," +
                "and you must enter a password");
    }


}
