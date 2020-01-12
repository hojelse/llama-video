package src.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import src.model.StreamingService;

import java.io.IOException;
import java.util.Optional;

public class MainViewController {

    @FXML
    BorderPane root;
    @FXML
    ToggleButton searchBtn;
    @FXML
    ToggleButton plusBtn;
    @FXML
    AnchorPane contentPane;
    @FXML
    Label accountUserName;

    Pane lastPane;

    public static MainViewController mainViewController;

    private MainViewController(){}

    public static MainViewController getInstance(){
        if(mainViewController == null){
            mainViewController = new MainViewController();
        }
        return mainViewController;
    }

    public void initialize(){
        getInstance();
        ToggleGroup nav = new ToggleGroup();
        searchBtn.setToggleGroup(nav);
        plusBtn.setToggleGroup(nav);
        searchBtn.setSelected(true);
        String userName = StreamingService.getInstance().getCurrentUser().getUsername();
        accountUserName.setText(userName);
    }

    @FXML
    private void searchBtn() throws IOException {
       setCurrentContent(FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("FXMLdocs/searchScene.fxml")));
    }

    @FXML
    private void plusBtn(ActionEvent actionEvent) throws IOException{
        setCurrentContent(FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("FXMLdocs/favoritesScene.fxml")));
    }

    @FXML
    private void accountBtn(ActionEvent actionEvent) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Logout or switch user");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            StreamingService.getInstance().logoutCurrentUser();
            SceneChanger.swapToLoginScene((Stage) root.getScene().getWindow());
        }
    }

    public void setCurrentContent(Pane pane) {
        if(contentPane.getChildren().size() > 0){
            lastPane = (Pane) contentPane.getChildren().get(0);
        }
        contentPane.getChildren().clear();
        contentPane.getChildren().add(pane);
    }

    public void goToLastPage(){
        setCurrentContent(lastPane);
    }

}
