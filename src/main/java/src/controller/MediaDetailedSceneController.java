package src.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import src.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public class MediaDetailedSceneController {

    @FXML
    AnchorPane root;
    @FXML
    VBox playWindowVBox;
    @FXML
    VBox playWindow;
    @FXML
    ToggleButton playPauseToggleButton;
    @FXML
    Group playPauseToggleButtonGraphic;
    @FXML
    Text titleText;
    @FXML
    Text ratingText;
    @FXML
    Text yearText;
    @FXML
    Text genreText;
    @FXML
    ToggleButton favToggleButton;
    @FXML
    Pane spacer;
    @FXML
    HBox titleBar;
    @FXML
    Button backButton;

    private boolean playing;
    private javafx.scene.media.Media media1;
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    private String title;

    public MediaDetailedSceneController(Media media){
        setUpFXML();

        backButton.setOnAction(actionEvent -> MainViewController.getInstance().goToLastPage());

        //TextDisplay
        title = media.getTitle();
        titleText.setText(title);

        titleBar.setHgrow(spacer, Priority.ALWAYS);

        ratingText.setText("★ " + media.getRating());

        yearText.setText("Year: " + media.getReleaseYear());

        StringBuilder genreStringBuilder = new StringBuilder("");
        for(String g : media.getGenres()){
            genreStringBuilder.append(g).append(", ");
        }

        genreText.setText(genreStringBuilder.toString());

        //FavToggleButton
        if(StreamingService.getInstance().getCurrentUser().isInFavorites(media)){
            favToggleButton.setSelected(true);
        } else {
            favToggleButton.setSelected(false);
        }
        favToggleButton.setOnAction(actionEvent -> {
            if(StreamingService.getInstance().getCurrentUser().isInFavorites(media)){
                StreamingService.getInstance().getCurrentUser().removeFromFavorites(media);
                favToggleButton.setSelected(false);
            } else {
                StreamingService.getInstance().getCurrentUser().addToFavorites(media);
                favToggleButton.setSelected(true);
            }
        });

        playPauseToggleButton.setOnAction(actionEvent -> {
            playing = !playing;
            updateButtonGraphic();
        });

        if(media instanceof Playable){
            makePlayScreen((Playable) media);
        }

        if(media instanceof Seasonable){
            displaySeasonableDetails((Seasonable) media);
        }


    }

    public void setUpFXML(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Thread.currentThread().getContextClassLoader().getResource("FXMLdocs/MediaDetailedScene.fxml"));
            loader.setController(this);
            loader.load();
            MainViewController.getInstance().setCurrentContent(root);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void updateButtonGraphic(){
        if(playing && mediaView != null){
            mediaView.getMediaPlayer().play();
            ((SVGPath) playPauseToggleButtonGraphic.getChildren().get(0)).setContent("M5,2.5 l 10,0 0,35 -10,0z");
            ((SVGPath) playPauseToggleButtonGraphic.getChildren().get(1)).setContent("M25,2.5 l 10,0 0,35 -10,0z");
        } else if (mediaView != null){
            mediaView.getMediaPlayer().pause();
            ((SVGPath) playPauseToggleButtonGraphic.getChildren().get(0)).setContent("M2.5,0 l 17.5,10 0,20 -17.5,10 z");
            ((SVGPath) playPauseToggleButtonGraphic.getChildren().get(1)).setContent("M20,10 l 20,10 -20,10 z");
        }
    }

    private void updateTitleText(Episode episode){
        titleText.setText(title + " S" + episode.getSeason() + ":E" + episode.getEpisodeNumber());
    }

    private void makePlayScreen(Playable media){
        media1 = new javafx.scene.media.Media(media.getMediaFilePath());
        mediaPlayer = new MediaPlayer(media1);
        mediaView = new MediaView(mediaPlayer);
        mediaView.setFitWidth(1100);
        playWindow.getChildren().clear();
        playWindow.getChildren().add(mediaView);
    }

    private void displaySeasonableDetails(Seasonable seasonable) {
        TreeMap<Integer, ArrayList<Episode>> seasons = seasonable.getSeasons();
        int numberOfSeasons = seasons.size();


        User currentUser = StreamingService.getInstance().getCurrentUser();
        String title = seasonable.getTitle();

        Episode reachedEpisode;
        if(currentUser.checkIfWatched(title)){
            reachedEpisode = currentUser.getReachedEpisode(title);
        } else {
            reachedEpisode = seasons.get(1).get(0);
        }
        updateTitleText(reachedEpisode);

        int reachedSeason = reachedEpisode.getSeason();
        int reachedEpisodeNumber = reachedEpisode.getEpisodeNumber();


        ToggleGroup tg = new ToggleGroup();
        for (int i = 1; i <= numberOfSeasons; i++) {
            if(seasons.containsKey(i)) {
                ArrayList<Episode> episodes = seasons.get(i);
                int episodesInSeason = episodes.size();

                Text t = new Text("Season " + i + " - " + episodesInSeason + " episodes");
                t.getStyleClass().add("seasonTitle");
                playWindowVBox.getChildren().add(t);
                FlowPane fp = new FlowPane();
                fp.getStyleClass().add("episodesFlowpane");
                playWindowVBox.getChildren().add(fp);

                for (int j = 0; j < episodesInSeason; j++) {
                    Episode episode = episodes.get(j);
                    RadioButton episodeRadioButton = new RadioButton();
                    episodeRadioButton.setToggleGroup(tg);
                    if(reachedSeason == episode.getSeason() && reachedEpisodeNumber == episode.getEpisodeNumber()) {
                        makePlayScreen(episode);
                        episodeRadioButton.setSelected(true);
                    }
                    episodeRadioButton.getStyleClass().add("episode");
                    episodeRadioButton.setText("S" + i + "E" + episode.getEpisodeNumber());
                    episodeRadioButton.setOnAction(actionEvent -> {
                        currentUser.addReachedEpisode(title, episode);
                        updateTitleText(episode);
                        playing = false;
                        mediaPlayer.pause();
                        updateButtonGraphic();
                        makePlayScreen(episode);
                    });
                    fp.getChildren().add(episodeRadioButton);
                }
            }
        }
    }
}
