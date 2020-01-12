package src.model;

import java.util.List;

public class Movie extends Media implements Playable{
    String mediaFilePath;

    public Movie(String title, int year, List<String> genres, double rating, String imagePath, String mediaFilePath) {
        super(title,year,genres,rating,imagePath);
        this.mediaFilePath = mediaFilePath;
    }

    @Override
    public String getMediaFilePath() {
        return mediaFilePath;
    }
}
