import java.util.*;

public class Flower extends Plant{
    private String name;
    private int size;
    private String season;
    private String color;
    
    public Flower() {
        this("Flower", "Pink");
    }

    public Flower(String name, String color) {
        this(name, 0, "Spring", color);
    }

    public Flower(String name, int size, String season, String color) {
        this.name = name;
        this.size = size;
        this.season = season;
        this.color = color;
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

    public int grow(String currSeason) {
        if (inSeason(currSeason)) {
            setSize(getSize() + 2);
        } else {
            setSize(getSize() + 1);
        }
        return getSize();
    }

    public String bloom(String currSeason) {
        if (!inSeason(currSeason)) {
            return "Not blooming season yet!";
        } else {
            return color;
        }
    }

    public String toString() {
        return "NAME: " + getName() + "; " + getSize() + " oz; " + getSeason() + "; " + getColor();
    }
}
