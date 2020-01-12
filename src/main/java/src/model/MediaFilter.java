package src.model;

import java.util.ArrayList;
import java.util.List;

public class MediaFilter {


    public static List<Media> filterByGenreChoiceBox(List<Media> mediaList, String genre){
        List<Media> filteredList = new ArrayList<>();

        for (Media media : mediaList) {
            if (media.getGenres().contains(genre)) {
                filteredList.add(media);
            }
        }
        return filteredList;

    }

    public static List<Media> filterBySearchField(List<Media> mediaList, String prefix){
        List<Media> filteredList = new ArrayList<>();

        for (Media media : mediaList) {
            String str = media.getTitle().toLowerCase();
            if (str.contains(prefix.toLowerCase())) {
                filteredList.add(media);
            }
        }
        return filteredList;

    }

    public static List<Media> sortBySortChoiceBox(List<Media> mediaList,String filter){
        switch (filter){
            case "Title":
                mediaList.sort(Media.Comparators.TITLE);
                break;
            case "Year":
                mediaList.sort(Media.Comparators.YEAR);
                break;
            case "Rating":
                mediaList.sort(Media.Comparators.RATING);
                break;
        }
        return mediaList;
    }






}
