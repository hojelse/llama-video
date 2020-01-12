package src.view;

import src.controller.MediaDetailedSceneController;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import src.model.Media;

public class MediaCard extends VBox implements EventHandler<MouseEvent> {


    Media media;

    public MediaCard(Media media){
        this.media = media;

        getStylesheets().add(getClass().getResource("/styles/mediaCard.css").toExternalForm());
        this.getStyleClass().add("mediaCard");

        ImageView imageView = new ImageView();

        // TODO Husk at håntere hvis der ikke findes et billede så brug default thumbnail

        Image img = new Image(media.getImagePath());
        imageView.setImage(img);

        imageView.setPreserveRatio(true);
        getChildren().add(imageView);

        Text title = new Text(media.getTitle());
        // todo kan det skrives i css?
        title.setWrappingWidth(130);
        title.getStyleClass().add("mediaTitle");
        getChildren().add(title);

        getChildren().add(new Text("Year: " + media.getReleaseYear()));

        Text Rating = new Text("★ " + media.getRating());
        getChildren().add(Rating);

        StringBuilder gen = new StringBuilder("Genres: ");
        for(String g : media.getGenres()){
            gen.append(g).append(" ");
        }
        Text genres = new Text(gen.toString());
        genres.setWrappingWidth(100);
        getChildren().add(genres);

        addEventHandler(MouseEvent.MOUSE_CLICKED, this);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        new MediaDetailedSceneController(media);
        /*
        ScrollPane sp = new ScrollPane(new MediaDetailedScene(media));

        sp.getStyleClass().add("favScrollPane");

        AnchorPane anchorPane = new AnchorPane(sp);
        AnchorPane.setTopAnchor(sp,0.0);
        AnchorPane.setBottomAnchor(sp,0.0);
        AnchorPane.setLeftAnchor(sp,0.0);
        AnchorPane.setRightAnchor(sp,0.0);

        AnchorPane.setTopAnchor(anchorPane,0.0);
        AnchorPane.setBottomAnchor(anchorPane,0.0);
        AnchorPane.setLeftAnchor(anchorPane,0.0);
        AnchorPane.setRightAnchor(anchorPane,0.0);

        MainViewController.getInstance().setCurrentContent(anchorPane);
        */
    }
}
