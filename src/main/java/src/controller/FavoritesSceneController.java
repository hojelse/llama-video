package src.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import src.model.Media;
import src.model.StreamingService;
import src.model.User;
import src.view.MediaCard;

public class FavoritesSceneController {

    @FXML
    FlowPane flowPane;
    @FXML
    AnchorPane root;

    public FavoritesSceneController(){
    }

    public void initialize(){
        User currentUser = StreamingService.getInstance().getCurrentUser();
        for(Media media : currentUser.getFavoriteMedias()){
            MediaCard mediaCard = new MediaCard(media);
            flowPane.getChildren().add(mediaCard);
        }

        MainViewController.getInstance().setCurrentContent(root);
    }

}
