package src.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneChanger {

    public static Scene lastScene;
    public static double lastSceneHeight;
    public static double lastSceneWidth;

    public static void swapToLoginScene(Stage stage){
        try {
            BorderPane pane = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("FXMLdocs/loginView.fxml"));

            if(lastScene == null){
                lastSceneHeight = 800;
                lastSceneWidth = 1300;
            } else {
                lastSceneHeight = lastScene.getHeight();
                lastSceneWidth = lastScene.getWidth();
            }
            Scene loginScreen = new Scene(pane, lastSceneWidth, lastSceneHeight);

            lastScene = loginScreen;

            loginScreen.getStylesheets().add(SceneChanger.class.getResource("/styles/style.css").toExternalForm());

            stage.setScene(loginScreen);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void swapToSignupScene(Stage stage){
        try {
            BorderPane pane = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("FXMLdocs/signupView.fxml"));

            lastSceneHeight = lastScene.getHeight();
            lastSceneWidth = lastScene.getWidth();

            Scene loginScreen = new Scene(pane, lastSceneWidth, lastSceneHeight);

            lastScene = loginScreen;

            loginScreen.getStylesheets().add(SceneChanger.class.getResource("/styles/style.css").toExternalForm());

            stage.setScene(loginScreen);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void swapToMainScene(Stage stage){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Thread.currentThread().getContextClassLoader().getResource("FXMLdocs/mainView.fxml"));
            loader.setController(MainViewController.getInstance());

            BorderPane pane = loader.load();

            AnchorPane defaultContentRoot;
            defaultContentRoot = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("FXMLdocs/searchScene.fxml"));
            AnchorPane gp = (AnchorPane) pane.lookup("#contentPane");
            gp.getChildren().add(defaultContentRoot);

            lastSceneHeight = lastScene.getHeight();
            lastSceneWidth = lastScene.getWidth();

            Scene mainScene = new Scene(pane, lastSceneWidth, lastSceneHeight);

            lastScene = mainScene;

            mainScene.getStylesheets().add(SceneChanger.class.getResource("/styles/style.css").toExternalForm());

            stage.setScene(mainScene);
        } catch (IOException e){
            e.printStackTrace();
        }

    }

}
