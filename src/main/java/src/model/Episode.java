package src.model;

import java.io.Serializable;

public class Episode implements Playable, Serializable {

    private String mediaFilePath;
    private int season;
    private int episodeNumber;

    public Episode(int season, int episodeNumber, String mediaFilePath){
        this.season = season;
        this.episodeNumber = episodeNumber;
        this.mediaFilePath = mediaFilePath;
    }


    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public int getSeason() {
        return season;
    }

    public String getMediaFilePath() {
        return mediaFilePath;
    }
}
