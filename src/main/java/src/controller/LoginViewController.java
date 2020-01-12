package src.controller;

import src.Exceptions.InvalidPasswordException;
import src.Exceptions.UserDoesNotExistException;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import src.model.DatabaseHandler;


public class LoginViewController {

    @FXML
    BorderPane root;
    @FXML
    TextField userNameTextField;
    @FXML
    PasswordField passwordPasswordField;
    @FXML
    Button loginButton;
    @FXML
    Button signupInstead;
    @FXML
    Group alpaca;
    @FXML
    Group arms;
    @FXML
    SVGPath leftEye;
    @FXML
    SVGPath rightEye;
    @FXML
    Group mouthGroup;
    @FXML
    Group earGroup;
    @FXML
    Label status;

    public LoginViewController(){
    }

    public void initialize(){
        status.setWrapText(true);
        alpaca.translateYProperty().setValue(-130);

        userNameTextField.setOnAction(actionEvent -> {
            tryToLogin();
        });
        passwordPasswordField.setOnAction(actionEvent -> {
            tryToLogin();
        });
        loginButton.setOnAction(actionEvent -> {
            tryToLogin();
        });
        signupInstead.setOnAction(actionEvent -> {
            SceneChanger.swapToSignupScene((Stage) root.getScene().getWindow());
        });
        userNameTextField.textProperty().addListener((observableValue, s, t1) -> {
            if (userNameTextField.getText().length() <= 0){
                rightEye.translateXProperty().setValue(0);
                leftEye.translateXProperty().setValue(0);
                mouthGroup.translateXProperty().setValue(0);
                earGroup.translateXProperty().setValue(0);

                rightEye.translateYProperty().setValue(0);
                leftEye.translateYProperty().setValue(0);
                mouthGroup.translateYProperty().setValue(0);
            } else if (userNameTextField.getText().length() >= 28) {
                double offset = 8;
                rightEye.translateXProperty().setValue(offset);
                leftEye.translateXProperty().setValue(offset);
                mouthGroup.translateXProperty().setValue(offset*1.2);
                earGroup.translateXProperty().setValue(-offset*1.1);

                rightEye.translateYProperty().setValue(5);
                leftEye.translateYProperty().setValue(5);
                mouthGroup.translateYProperty().setValue(5);
            } else {
                double offset = 16 * (userNameTextField.getText().length()/28.0) - 8;
                rightEye.translateXProperty().setValue(offset);
                leftEye.translateXProperty().setValue(offset);
                mouthGroup.translateXProperty().setValue(offset*1.2);
                earGroup.translateXProperty().setValue(-offset*1.1);

                rightEye.translateYProperty().setValue(5);
                leftEye.translateYProperty().setValue(5);
                mouthGroup.translateYProperty().setValue(5);
            }
        });
        userNameTextField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            arms.translateYProperty().setValue(135);
        });
        passwordPasswordField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            rightEye.translateXProperty().setValue(0);
            leftEye.translateXProperty().setValue(0);
            mouthGroup.translateXProperty().setValue(0);
            earGroup.translateXProperty().setValue(0);
            arms.translateYProperty().setValue(0);

            rightEye.translateYProperty().setValue(0);
            leftEye.translateYProperty().setValue(0);
            mouthGroup.translateYProperty().setValue(0);
        });
    }


    public void tryToLogin() {
        String userName = userNameTextField.getText();
        String password = passwordPasswordField.getText();
       if(userName.length() > 0) {
            try {
                DatabaseHandler.attemptLogin(userName, password);
                SceneChanger.swapToMainScene((Stage) root.getScene().getWindow());
            } catch (UserDoesNotExistException | InvalidPasswordException e) {
                userNameTextField.getStyleClass().add("userNameInvalid");
                passwordPasswordField.getStyleClass().add("passwordInvalid");
                status.setText(e.getMessage());
            }
        }
    }
}



