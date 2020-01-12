package src.model;


import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class StreamingService {

    private static StreamingService streamingService;

    private List<Movie> movieList;
    private List<Series> seriesList;
    private List<Media> mediaList;
    private List<Media> filteredMediaList;
    private List<Media> currentlySelectedMedia;
    private List<String> uniqueGenre;
    private User currentUser;

    private StreamingService(){

        DataReader dataReader = new DataReader();
        mediaList = new ArrayList<>();
        uniqueGenre = new ArrayList<>();
        currentlySelectedMedia = new ArrayList<>();
        filteredMediaList = new ArrayList<>();

        try{
        movieList = dataReader.readMovies();
        seriesList = dataReader.readSeries();
        } catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("There was an error reading the media data. The program will now shutdown. Please reinstall");
            alert.showAndWait();
            System.exit(1);
        }

        //Add all Lists to media
        mediaList.addAll(movieList);
        mediaList.addAll(seriesList);
        resetLists();
    }

    public static StreamingService getInstance(){
        if(streamingService == null){
            streamingService = new StreamingService();
        }
        return streamingService;
    }

    public void resetLists(){
        currentlySelectedMedia.clear();
        currentlySelectedMedia.addAll(mediaList);
    }

    // User methods
    public void logoutCurrentUser(){
        DatabaseHandler.serializeUser(currentUser);
        currentUser = null;
    }

    public void setCurrentUser(User user){
        currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    // Filter methods
    public void filterCurrentView(String genre,String searchPrefix, String sortingText){
        filteredMediaList = currentlySelectedMedia;
        if(!searchPrefix.equals("")) {
            filteredMediaList = MediaFilter.filterBySearchField(filteredMediaList, searchPrefix);
        }
        if(!genre.equals("All genres")) {
            filteredMediaList = MediaFilter.filterByGenreChoiceBox(filteredMediaList, genre);
        }
        filteredMediaList = MediaFilter.sortBySortChoiceBox(filteredMediaList, sortingText);
    }

    public void addMoviesToCurrentlySelected(){
        currentlySelectedMedia.addAll(movieList);
    }

    public void addSeriesToCurrentlySelected(){
        currentlySelectedMedia.addAll(seriesList);
    }

    public void removeSeriesFromCurrentlySelected(){
        currentlySelectedMedia.removeAll(seriesList);
    }

    public void removeMoviesFromCurrentlySelected(){
        currentlySelectedMedia.removeAll(movieList);
    }

    //This makes a list of all unique genres from the txt files
    public List<String> makeUniqueGenres(){
        uniqueGenre.clear();
        uniqueGenre.add("All genres");
        List<String> temp = new ArrayList<>();
        for(Media media : mediaList){
            for (String genre : media.getGenres()){
                if(!temp.contains(genre)){
                    temp.add(genre);
                }
            }
        }
        Collections.sort(temp);
        uniqueGenre.addAll(temp);
        return uniqueGenre;
    }


    public List<Media> getFilteredMediaList() {
        return filteredMediaList;
    }
}
