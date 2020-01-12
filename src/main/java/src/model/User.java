package src.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User implements Serializable {

    private String username;
    private String password;
    private List<Media> favoriteMedias;
    private HashMap<String,Episode> reachedEpisode;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        favoriteMedias = new ArrayList<>();
        reachedEpisode = new HashMap<>();
    }

    public void addToFavorites(Media media){
        if (!isInFavorites(media)){
            favoriteMedias.add(media);
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Media> getFavoriteMedias() {
        return favoriteMedias;
    }

    public void removeFromFavorites(Media media){
        favoriteMedias.remove(media);
    }

    public boolean isInFavorites(Media media){
        return favoriteMedias.contains(media);
    }

    public void addReachedEpisode(String title, Episode episode){
        reachedEpisode.put(title,episode);
    }

    public boolean checkIfWatched(String title){
        return reachedEpisode.containsKey(title);
    }

    public Episode getReachedEpisode(String title){
        return reachedEpisode.get(title);
    }


}
