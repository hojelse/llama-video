package src.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import src.model.Media;
import src.model.StreamingService;
import src.view.MediaCard;

import java.util.Optional;

public class SearchSceneController {

    private StreamingService streamingService;

    // Elements from fxml that has fx:id
    @FXML
    private FlowPane flowPane;
    @FXML
    private ChoiceBox sortChoiceBox;
    @FXML
    private ToggleButton showMoviesButton;
    @FXML
    private ToggleButton showSeriesButton;
    @FXML
    private TextField searchField;
    @FXML
    private ChoiceBox genreChoiceBox;
    @FXML
    Pane spacer;
    @FXML
    HBox advancedSearch;
    @FXML
    AnchorPane root;


    public SearchSceneController(){
        streamingService = StreamingService.getInstance();
    }

    // A method that runs at some point
    public void initialize(){
        flowPane.setOrientation(Orientation.HORIZONTAL);
        flowPane.setVgap(5);
        flowPane.setHgap(5);
        genreChoiceBox.setValue("All genres");
        genreChoiceBox.setItems(FXCollections.observableArrayList(streamingService.makeUniqueGenres()));
        advancedSearch.setHgrow(spacer, Priority.ALWAYS);

        searchField.textProperty().addListener((observableValue, s, t1) -> {
            if(searchField.getText().equals("bornholmer")){
                easterEggSuperDan();
            }
            filter();
        });
        sortChoiceBox.setOnAction(actionEvent -> filter());
        genreChoiceBox.setOnAction(actionEvent -> filter());
        showMoviesButton.setSelected(true);
        showSeriesButton.setSelected(true);

        // TODO make 'minimum one selected' scalable with more mediatypes
        showMoviesButton.setOnAction(actionEvent -> {
            if(showMoviesButton.selectedProperty().get()){
                streamingService.addMoviesToCurrentlySelected();
            } else {
                streamingService.removeMoviesFromCurrentlySelected();
                if(!showSeriesButton.selectedProperty().get()){
                    streamingService.addSeriesToCurrentlySelected();
                    showSeriesButton.setSelected(true);
                }
            }
            filter();
        });

        showSeriesButton.setOnAction(actionEvent -> {
            if(showSeriesButton.selectedProperty().get()){
                streamingService.addSeriesToCurrentlySelected();
            } else {
                streamingService.removeSeriesFromCurrentlySelected();
                if(!showMoviesButton.selectedProperty().get()){
                    streamingService.addMoviesToCurrentlySelected();
                    showMoviesButton.setSelected(true);
                }
            }
            filter();
        });

        streamingService.resetLists();
        filter();
    }

    public void filter(){
        String genre = (String) genreChoiceBox.getValue();
        String searchPrefix = searchField.getText();
        String filter = (String) sortChoiceBox.getValue();

        streamingService.filterCurrentView(genre,searchPrefix,filter);;
        showMedia();
    }

    public void showMedia() {
        flowPane.getChildren().clear();
        for(Media media : streamingService.getFilteredMediaList()){
            MediaCard mediaCard = new MediaCard(media);
            flowPane.getChildren().add(mediaCard);
        }
    }

    public void easterEggSuperDan(){
        ButtonType plus1 = new ButtonType("+1", ButtonBar.ButtonData.OK_DONE);
        ButtonType minus1 = new ButtonType("-1", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert a = new Alert(Alert.AlertType.CONFIRMATION,"", plus1, minus1);
        a.setHeaderText("Super Dan");
        Image image = new Image(getClass().getResource("/superdan.jpg").toExternalForm());
        ImageView imageView = new ImageView(image);
        a.setGraphic(imageView);
        showDan(a);
    }

    public void showDan(Alert a){
        Optional<ButtonType> result = a.showAndWait();
        if(!result.isPresent()){}
        else if (result.get().getButtonData() == ButtonBar.ButtonData.CANCEL_CLOSE) {
            showDan(a);
        }
    }

}
