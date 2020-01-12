package src;

import src.controller.SceneChanger;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import src.model.DatabaseHandler;
import src.model.StreamingService;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    /**
     * start() method prepares the initial src.view, loginView,
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {


        primaryStage.setTitle("Llama Video");

        primaryStage.getIcons().add(new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("logo.png")));

        DatabaseHandler.setPath(getPathToSaveDataFolder());
        DatabaseHandler.createSaveDataFolder();
        StreamingService.getInstance();
        SceneChanger.swapToLoginScene(primaryStage);

        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(300);
        primaryStage.show();

        primaryStage.setOnCloseRequest(e -> {
            if(StreamingService.getInstance().getCurrentUser() != null) {
                StreamingService.getInstance().logoutCurrentUser();
            }
            System.exit(0);
        });

    }

    /**
     * gets path of the saveData folder
     * @return path of saveData folder
     */
    public static String getPathToSaveDataFolder(){
        // Gets path of jar
        String pathToDir = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        try {
            pathToDir = URLDecoder.decode(pathToDir, "UTF-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        String jarName = System.getProperty("java.class.path");
        pathToDir = pathToDir.replace(jarName,"");
        String pathToSaveDataFolder = pathToDir + "saveData";
        pathToSaveDataFolder = pathToSaveDataFolder.substring(1);

        return pathToSaveDataFolder;
    }


}



