package src.controller;

import src.Exceptions.InvalidSignupCredentials;
import src.Exceptions.UsernameTakenException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import src.model.DatabaseHandler;
import src.model.User;

public class SignupViewController {

    @FXML
    BorderPane root;
    @FXML
    Button signupButton;
    @FXML
    Button loginInstead;
    @FXML
    TextField userNameTextField;
    @FXML
    PasswordField passwordPasswordField;
    @FXML
    Label status;

   public void initialize(){

       status.setWrapText(true);

       userNameTextField.setOnAction(actionEvent -> attemptSignup());
       passwordPasswordField.setOnAction(actionEvent -> attemptSignup());
       signupButton.setOnAction(actionEvent -> attemptSignup());
       loginInstead.setOnAction(actionEvent -> SceneChanger.swapToLoginScene((Stage) root.getScene().getWindow()));
    }



    public void attemptSignup() {
       String username = userNameTextField.getText();
       String password = passwordPasswordField.getText();
        try {
            if (username.matches("[a-zA-Z0-9.\\-_]{3,}") && password.length() > 0) {
                if(DatabaseHandler.userExists(username)){
                    throw new UsernameTakenException(username);
                } else {
                    User newUser = new User(username, password);
                    DatabaseHandler.serializeUser(newUser);
                    passwordPasswordField.getStyleClass().remove("passwordInvalid");
                    userNameTextField.getStyleClass().remove("userNameInvalid");
                    SceneChanger.swapToLoginScene((Stage) root.getScene().getWindow());
                }
            } else {
                throw new InvalidSignupCredentials();
            }
        } catch (UsernameTakenException | InvalidSignupCredentials e){
            userNameTextField.getStyleClass().add("userNameInvalid");
            passwordPasswordField.getStyleClass().add("passwordInvalid");
            status.setText(e.getMessage());
        }
    }

}
