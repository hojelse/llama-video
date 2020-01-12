package src.model;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Series extends Media implements Seasonable {

    private TreeMap<Integer, ArrayList<Episode>> seasons;
    private int latestReleaseYear;


    public Series(String title, int releaseYear, int latestReleaseYear, List<String> genres, double rating, TreeMap<Integer, ArrayList<Episode>> seasons, String imagePath) {
        super(title, releaseYear, genres, rating, imagePath);
        this.seasons = seasons;
        this.latestReleaseYear = latestReleaseYear;
    }

    public TreeMap<Integer, ArrayList<Episode>> getSeasons() {
        return seasons;
    }
}
