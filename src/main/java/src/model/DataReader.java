package src.model;

import java.io.*;
import java.util.*;

public class DataReader {

    private InputStream stream;
    private BufferedReader br;
    private OutputStream outputStream;
    private BufferedWriter bw;

    public DataReader(){

    }

    /**
     * readMovies reads from 'film', parses, creates movie objects and returns a list of movies
     * @return List of movies
     */

    public List<Movie> readMovies() throws IOException{
        stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("film");
        br = new BufferedReader(new InputStreamReader(stream));
        List<Movie> movieList = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null) {

            String[] information = line.split(";");

            String title = information[0];

            information = trimInArray(information);

            // parsing string to int
            int year = Integer.parseInt(information[1]);

            // Making genreList
            List<String> genreList = makeGenreList(information[2].split(","));


            // Parsing that to a double
            double rating = Double.parseDouble(information[3].replace(',', '.'));

            String imagePath = "";
            try {
                imagePath = this.getClass().getResource("/filmPlakater/" + title + ".jpg").toExternalForm();
            } catch (NullPointerException e){
                imagePath = this.getClass().getResource("/filmPlakater/placeholder.jpg").toExternalForm();
            }

            String mediaPath = "";
            try {
                mediaPath = this.getClass().getResource("/mediaFile/file1.mp4").toExternalForm();
            } catch (NullPointerException e){

            }

            // Creating a movie object and adding it to the movie List
            Movie movie = new Movie(title, year, genreList, rating, imagePath, mediaPath);

            movieList.add(movie);

        }
        return movieList;
    }

    public List<Series> readSeries() throws IOException{
        stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("serier");
        br = new BufferedReader(new InputStreamReader(stream));
        List<Series> seriesList = new ArrayList<>();

        String line;
        while ((line = br.readLine()) != null) {

            // Vi splitter linjen til et string array
            String[] information = line.split(";");
            String title = information[0];

            information = trimInArray(information);

            // Setting release year
            String yearToRead = information[1];

            int releaseYear;
            int latestYear;
            if (isYearAnInterval(yearToRead)) {
                // A starting year and ending year (e.g. "2001-2009")
                String[] years = information[1].split("-");
                releaseYear = Integer.parseInt(years[0]);
                latestYear = Integer.parseInt(years[1]);
            } else if (hasOnlyReleaseYear(yearToRead)){
                // A starting year not ended (e.g. "2001-")
                releaseYear = Integer.parseInt(information[1].substring(0, 4));
                latestYear = Calendar.getInstance().get(Calendar.YEAR);
            } else {
                // A single year (e.g. "2001")
                releaseYear = Integer.parseInt(information[1]);
                latestYear = Integer.parseInt(information[1]);
            }

            // Setting genrer
            String genres = information[2];
            List<String> genreList = makeGenreList(genres.split(","));

            // Setting rating
            String ratingString = information[3];

            double rating = Double.parseDouble(ratingString.replace(',', '.'));

            String[] seasonsArray = information[4].split(",");
            TreeMap<Integer, ArrayList<Episode>> seasons = new TreeMap<>();



            for (int i = 0; i < seasonsArray.length; i++) {
                String[] seasonSplit = seasonsArray[i].split("-");
                int season = Integer.parseInt(seasonSplit[0].trim());
                int episodesInSeason = Integer.parseInt(seasonSplit[1]);

                ArrayList<Episode> episodeArrayList = new ArrayList<>();

                for (int j = 1; j <= episodesInSeason; j++) {

                    String mediaPath = "";
                    try {
                        mediaPath = this.getClass().getResource("/mediaFile/file1.mp4").toExternalForm();
                    } catch (NullPointerException e){

                    }

                    Episode episode = new Episode(i+1, j, mediaPath);
                    episodeArrayList.add(episode);
                }
                seasons.put(season,episodeArrayList);
            }

            String imagePath = "";
            try {
                imagePath = this.getClass().getResource("/seriePlakater/" + title + ".jpg").toExternalForm();
            } catch (NullPointerException e){
                imagePath = this.getClass().getResource("/seriePlakater/placeholder.jpg").toExternalForm();
            }
            seriesList.add(new Series(title, releaseYear, latestYear, genreList, rating, seasons, imagePath));
        }

        return seriesList;
    }




    private boolean hasOnlyReleaseYear(String information) {
        return information.length() == 5;
    }

    private boolean isYearAnInterval(String information) {
        return information.length() > 5;
    }

    public List<String> makeGenreList(String[] genres){
        genres = trimInArray(genres);
        List<String> genreList = new ArrayList<>();
        genreList.addAll(Arrays.asList(genres));
        return genreList;
    }

    public String[] trimInArray(String[] s){
        for (int i = 0; i < s.length; i++) {
            s[i] = s[i].replace(" ", "");
        }
        return s;
    }
}
