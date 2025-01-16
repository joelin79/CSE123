// 123
// Section 2: Abstract Classes and Quality
// TA: 

// This class represents a Location object, storing a name, latitude, and longitude. 
// It implements the Comparable interface.
public class Location implements Comparable<Location> {
    private String name;
    private double latitude;
    private double longitude;

    // Takes in n and assigns it to the name field.
    public Location(String n, double la, double lo) {
        name = n;
        latitude = la;
        longitude = lo;
    }

    // Returns the name field
    public String getName() {
        return name;
    }

    // Returns the latitude of this Location as a double.
    public double getLatitude() {
        return latitude;
    }

    // Behavior: Gets the longitude of the Location. Exceptions: Throws no exceptions. Returns: The longitude of the Location as a double. Parameters: Takes in no parameters.
    public double getLongitude() {
        return longitude;
    }

    // Returns a string.
    public String toString() {
        String r = name + " " + Math.abs(latitude);
        if (latitude < 0) {
        r += "S";
        } else {
        r += "N";
        }
        
        r += " " + Math.abs(longitude);
        if (longitude < 0) {
        r += "E";
        } else {
        r += "W";
        }
        return r;
    }

    @Override
    // Behavior: Compares this Location to another Location
    // Exceptions: N/A
    // Returns: an integer representing the comparison between the two
    // Parameters: Takes in the other Location to compare to
    public int compareTo(Location other) {
        if (Math.abs(this.latitude) != Math.abs(other.latitude)) {
            if (Math.abs(this.latitude) < Math.abs(other.latitude)) {
                return -1;
            } else { // latitude > other.latitude
                return 1;
            }
        } else {
        if (Math.abs(this.longitude) < Math.abs(other.longitude)) {
            return -1;
        } else if (Math.abs(longitude) > Math.abs(other.longitude)) {
            return 1;
        } else { // longitude == other.longitude
            return 0;
        }
        }
    }
}
