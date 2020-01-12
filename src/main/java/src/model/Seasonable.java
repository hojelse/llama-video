package src.model;

import java.util.ArrayList;
import java.util.TreeMap;

public interface Seasonable {
    public String getTitle();
    public TreeMap<Integer, ArrayList<Episode>> getSeasons();
}
