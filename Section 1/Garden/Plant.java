import java.util.*;

public class Plant {
    private String name;
    private int size;
    private String season;
    
    public Plant() {
        this("Plant");
    }

    public Plant(String name) {
        this(name, 0, "Spring");
    }

    public Plant(String name, int size, String season) {
        this.name = name;
        this.size = size;
        this.season = season;
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

    public int grow(String currSeason) {
        size++;
        return size;
    }

    public String toString() {
        return "NAME: " + name + "; " + size + " oz; " + season;
    }
}
