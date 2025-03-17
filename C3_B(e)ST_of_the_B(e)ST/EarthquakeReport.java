// Joe Lin
// 03/13/2025
// CSE 123
// C3: B(e)ST of the B(e)ST
// TA: Benoit Le

import java.util.*;
import java.time.*;
import java.time.format.*;

/**
 * The EarthquakeReport class represents an earthquake report with various attributes
 * such as location, magnitude, depth, and timestamp. This class implements the Comparable
 * interface.
 */
public class EarthquakeReport implements Comparable<EarthquakeReport> {
    private final String location;
    private final double magnitude;
    private final double depth;
    private final int timestamp;

    /**
     * Constructs an EarthquakeReport object with the specified attributes.
     *
     * @param location - the location where the earthquake occurred
     * @param magnitude - the magnitude of the earthquake (non-negative)
     * @param depth - the depth of the earthquake in kilometers (non-negative)
     * @param timestamp - the Unix timestamp when the earthquake occurred (in seconds)
     */
    public EarthquakeReport(String location, double magnitude, double depth, int timestamp) {
        if (magnitude < 0 || depth < 0) {
            throw new IllegalArgumentException("Magnitude and Depth cannot be negative!");
        }

        this.location = location;
        this.magnitude = magnitude;
        this.depth = depth;
        this.timestamp = timestamp;
    }

    /**
     * Clone a new EarthquakeReport from another.
     *
     * @param report - the EarthquakeReport object to copy
     */
    public EarthquakeReport(EarthquakeReport report) {
        this(report.location, report.magnitude, report.depth, report.timestamp);
    }

    /**
     * Returns the magnitude value of this EarthquakeReport
     *
     * @return the magnitude of the earthquake
     */
    public double getMagnitude() {
        return magnitude;
    }

    /**
     * Returns the depth value of this EarthquakeReport
     *
     * @return the depth of the earthquake
     */
    public double getDepth(){
        return depth;
    }

    /**
     * Returns the timestamp value of this EarthquakeReport
     *
     * @return the timestamp of the earthquake
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the location of this EarthquakeReport
     *
     * @return the location of the earthquake
     */
    public String getLocation() {
        return location;
    }

    /**
     * Prompts the user for input to create a new EarthquakeReport object.
     *
     * @param input the Scanner object used to read user input
     * @return a new EarthquakeReport with the provided attributes from user input
     * @throws IllegalArgumentException if input is null
     */
    public static EarthquakeReport parse(Scanner input) {
        if (input == null) {
            throw new IllegalArgumentException("Scanner input cannot be null");
        }

        System.out.print("Enter the earthquake location: ");
        String location = input.nextLine();

        System.out.print("Enter the magnitude: ");
        double magnitude = Double.parseDouble(input.nextLine());

        System.out.print("Enter the depth (in km): ");
        double depth = Double.parseDouble(input.nextLine());

        System.out.print("Enter the timestamp (Unix seconds): ");
        int timestamp = Integer.parseInt(input.nextLine());

        return new EarthquakeReport(location, magnitude, depth, timestamp);
    }

    /**
     * Converts a Unix timestamp to a formatted date-time string.
     *
     * @param timestamp - the Unix timestamp to convert
     * @return a formatted date-time string in Taipei time (GMT+8 aka Asia/Taipei)
     */
    private static String convertUnixToDateTime(int timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
                .withZone(ZoneId.of("Asia/Taipei"));
        return formatter.format(Instant.ofEpochSecond(timestamp));
    }

    /**
     * Returns a string representation of the EarthquakeReport.
     * Format (no line break):
     * M[magnitude] earthquake (depth: [depth] km) in [location]
     *                                      at [yyyy/MM/dd HH:mm:ss] (Unix [Unix])
     *
     * @return a string representation of the EarthquakeReport
     */
    @Override
    public String toString() {
        return String.format("M%.1f earthquake (depth: %.1f km) in %s at %s (Unix %d)",
                magnitude, depth, location, convertUnixToDateTime(timestamp), timestamp);
    }

    /**
     * Compares this EarthquakeReport to another EarthquakeReport primarily by higher magnitude,
     * then by shallower depth, and finally by timestamp (recent) and location (alphabetical).
     *
     * @param other - the EarthquakeReport to compare to
     * @return a negative integer, zero, or a positive integer as this EarthquakeReport is
     *         less than (comes before), equal to, or greater (comes after)
     *         than the specified EarthquakeReport
     */
    @Override
    public int compareTo(EarthquakeReport other) {
        // Primary sort by magnitude (descending - higher magnitude first)
        if (this.magnitude != other.magnitude) {
            return (magnitude > other.magnitude) ? -1 : 1;
        }

        // Secondary sort by depth (ascending - shallower earthquakes first)
        if (this.depth != other.depth) {
            return (depth < other.depth) ? -1 : 1;
        }

        // Tertiary sort by timestamp (most recent first)
        if (this.timestamp != other.timestamp) {
            return (timestamp > other.timestamp) ? -1 : 1;
        }

        // Finally sort by location
        return this.location.compareTo(other.location);
    }

    /**
     * Checks if this EarthquakeReport is equal to another object.
     * Two EarthquakeReports are considered equal when they have the same magnitude,
     * depth, location, and timestamp.
     *
     * @param o - the object to compare to
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EarthquakeReport)) {
            return false;
        }

        EarthquakeReport other = (EarthquakeReport) o;
        return this.magnitude == other.magnitude
                && this.depth == other.depth
                && this.timestamp == other.timestamp
                && this.location.equals(other.location);
    }

    /**
     * Returns a unique hash code value for this EarthquakeReport.
     *
     * @return a hash code value for this EarthquakeReport
     */
    @Override
    public int hashCode() {
        int result = 31 * location.hashCode();
        result = 31 * result + Double.hashCode(magnitude);
        result = 31 * result + Double.hashCode(depth);
        result = 31 * result + Integer.hashCode(timestamp);
        return result;
    }
}