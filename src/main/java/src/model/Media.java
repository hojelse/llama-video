package src.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

public abstract class Media implements Comparable<Media>, Serializable {

    private String title;
    private int releaseYear;
    private List<String> genres;
    private double rating;
    private String imagePath;


    public Media(String title, int releaseYear, List<String> genres, double rating, String imagePath) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.genres = genres;
        this.rating = rating;
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public List<String> getGenres() {
        return genres;
    }

    public double getRating() {
        return rating;
    }

    public String getImagePath(){
        return imagePath;
    }

    // todo ryk til src.model?
    @Override
    public int compareTo(Media o) {
        return Comparators.TITLE.compare(this,o);
    }

    public static class Comparators  {

        public static Comparator<Media> TITLE = new Comparator<Media>() {

            @Override
            public int compare(Media o1, Media o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
            // hvad returnere compareTo()?
        };

        public static Comparator<Media> YEAR = new Comparator<Media>() {
            @Override
            public int compare(Media o1, Media o2) {
                return o2.getReleaseYear() - o1.getReleaseYear();
            }
        };

        public static Comparator<Media> RATING = new Comparator<Media>() {
            @Override
            public int compare(Media o1, Media o2) {
                int ratingo1 = (int) (o1.getRating()*10);
                int ratingo2 = (int) (o2.getRating()*10);

                return ratingo2 - ratingo1;
            }
        };

    }




}
