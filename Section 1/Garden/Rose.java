import java.util.*;

public class Rose extends Flower{
    private String name;
    private int size;
    private String season;
    private String color;
    private int numThorns;
    
    public Rose() {
        this("Rose", "Red");
    }

    public Rose(String name, String color) {
        this(name, 0, "Spring", color);
    }

    public Rose(String name, int size, String season, String color) {
        this.name = name;
        this.size = size;
        this.season = season;
        this.color = color;
        this.numThorns = 0;
    }

    public String getName() {
        return name;
    }

    public void rename(String newName) {
        name = newName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int newSize) {
        size = newSize;
    }

    public String getSeason() {
        return season;
    }

    public boolean inSeason(String currSeason) {
        return season.equalsIgnoreCase(currSeason);
    }

    public String getColor() {
        return color;
    }
    
    public int getThorns() {
        return numThorns;
    }

    public int grow(String currSeason) {
        if (inSeason(currSeason)) {
            setSize(getSize() + 2);
            numThorns++;
        } else {
            setSize(getSize() + 1);
        }
        return getSize();
    }

    public int grow(String currSeason, int fertilizer) {
        if (inSeason(currSeason)) {
            setSize(getSize() + (2*fertilizer));
            numThorns++;
        } else {
            setSize(getSize() + fertilizer);
        }
        return getSize();
    }

    public String bloom(String currSeason) {
        if (!inSeason(currSeason)) {
            return "Not blooming season yet!";
        } else {
            return getColor();
        }
    }
    
    public String toString() {
        return "NAME: " + getName() + "; " + getSize() + " oz; " + getSeason() + "; " + getColor();
    }
}
