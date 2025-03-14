// Joe Lin
// 03/13/2025
// CSE 123
// C3: B(e)ST of the B(e)ST
// TA: Benoit Le

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;


/**
 * This class is for JUnit testing the CollectionManager class with EarthquakeReport
 * */
public class Testing {

    // Test for EarthquakeReport constructor and basic equality
    @Test
    public void testEarthquakeReportCreation() {
        EarthquakeReport report = new EarthquakeReport("Hualien, Taiwan", 7.2, 15.8, 10000);
        assertEquals("M7.2 earthquake in Hualien, Taiwan at timestamp 10000 (depth: 15.8 km)", report.toString());
    }

    // Test for EarthquakeReport compareTo method
    @Test
    public void testEarthquakeReportCompareTo() {
        EarthquakeReport report1 = new EarthquakeReport("Hualien, Taiwan", 7.2, 15.8, 100000);
        EarthquakeReport report2 = new EarthquakeReport("Yilan, Taiwan", 6.9, 12.5, 90000);
        EarthquakeReport report3 = new EarthquakeReport("Chiayi, Taiwan", 7.2, 10.5, 80000);

        // Test magnitude comparison (higher first)
        assertTrue(report1.compareTo(report2) < 0);

        // Test equal magnitude but different depth (shallower first)
        assertTrue(report3.compareTo(report1) < 0);

        // Test equality
        EarthquakeReport reportCopy = new EarthquakeReport("Hualien, Taiwan", 7.2, 15.8, 100000);
        assertEquals(0, report1.compareTo(reportCopy));
    }

    // Test for EarthquakeReport equals method
    @Test
    public void testEarthquakeReportEquals() {
        EarthquakeReport report1 = new EarthquakeReport("Hualien, Taiwan", 7.2, 15.8, 100000);
        EarthquakeReport report2 = new EarthquakeReport("Hualien, Taiwan", 7.2, 15.8, 100000);
        EarthquakeReport report3 = new EarthquakeReport("Hualien, Taiwan", 7.1, 15.8, 100000);

        // Test equality
        assertEquals(report1, report2);

        // Test inequality
        assertNotEquals(report1, report3);
        assertNotEquals(report1, null);
        assertNotEquals(report1, "Not an earthquake report");
    }

    // Test for EarthquakeReport hashCode method
    @Test
    public void testEarthquakeReportHashCode() {
        EarthquakeReport report1 = new EarthquakeReport("Hualien, Taiwan", 7.2, 15.8, 100000);
        EarthquakeReport report2 = new EarthquakeReport("Hualien, Taiwan", 7.2, 15.8, 100000);

        // Equal objects should have equal hash codes
        assertEquals(report1.hashCode(), report2.hashCode());
    }

    // Test for CollectionManager empty constructor
    @Test
    public void testEmptyCollectionManager() {
        CollectionManager manager = new CollectionManager();
        assertEquals("Empty earthquake collection", manager.toString());
    }

    // Test for CollectionManager add method
    @Test
    public void testAddToCollectionManager() {
        CollectionManager manager = new CollectionManager();
        EarthquakeReport report = new EarthquakeReport("Hualien, Taiwan", 7.2, 15.8, 100000);

        manager.add(report);
        assertTrue(manager.contains(report));

        // Test adding a null report
        assertThrows(IllegalArgumentException.class, () -> manager.add(null));
    }

    // Test for CollectionManager contains method
    @Test
    public void testContainsInCollectionManager() {
        CollectionManager manager = new CollectionManager();
        EarthquakeReport report1 = new EarthquakeReport("Hualien, Taiwan", 7.2, 15.8, 100000);
        EarthquakeReport report2 = new EarthquakeReport("Yilan, Taiwan", 7.2, 15.8, 100000);

        manager.add(report1);

        assertTrue(manager.contains(report1));
        assertFalse(manager.contains(report2));

        // Test contains with a null report
        assertThrows(IllegalArgumentException.class, () -> manager.contains(null));
    }

    // Test for CollectionManager Scanner constructor
    @Test
    public void testCollectionManagerFromScanner() {
        String input = "Hualien, Taiwan\n7.2\n15.8\n100000\n";
        Scanner scanner = new Scanner(input);

        CollectionManager manager = new CollectionManager(scanner);
        EarthquakeReport report = new EarthquakeReport("Hualien, Taiwan", 7.2, 15.8, 100000);

        assertTrue(manager.contains(report));
        assertThrows(IllegalArgumentException.class, () -> new CollectionManager(null));
    }

    // Test for CollectionManager toString method
    @Test
    public void testCollectionManagerToString() {
        CollectionManager manager = new CollectionManager();
        EarthquakeReport report1 = new EarthquakeReport("Hualien, Taiwan", 7.2, 15.8, 100000);
        EarthquakeReport report2 = new EarthquakeReport("Yilan, Taiwan", 6.2, 15.8, 900000);

        manager.add(report1);
        manager.add(report2);

        String result = manager.toString();
        System.out.println(result);
        assertTrue(result.contains("Hualien, Taiwan"));
        assertTrue(result.contains("Yilan, Taiwan"));
    }

    // Test for CollectionManager save method
    @Test
    public void testCollectionManagerSave() {
        CollectionManager manager = new CollectionManager();
        EarthquakeReport report = new EarthquakeReport("Hualien, Taiwan", 7.2, 15.8, 100000);

        manager.add(report);

        // Test save with null PrintStream
        assertThrows(IllegalArgumentException.class, () -> manager.save(null));
    }

    @Test
    public void testFilterByMagnitudeRange() {
        CollectionManager manager = new CollectionManager();
        manager.add(new EarthquakeReport("Hualien, Taiwan", 7.2, 15.8, 100000));
        manager.add(new EarthquakeReport("Tohoku, Japan", 9.0, 12.5, 200000));
        manager.add(new EarthquakeReport("Mexico City, Mexico", 5.6, 8.3, 300000));
        manager.add(new EarthquakeReport("Santiago, Chile", 6.9, 30.6, 400000));

        List<EarthquakeReport> filtered = manager.filter(6.0, 7.5);
        assertEquals(2, filtered.size());

        boolean containsTokyo = false;
        boolean containsSanFrancisco = false;
        for (EarthquakeReport report : filtered) {
            if (report.toString().contains("Hualien")) {
                containsTokyo = true;
            }
            if (report.toString().contains("Santiago")) {
                containsSanFrancisco = true;
            }
        }
        assertTrue(containsTokyo);
        assertTrue(containsSanFrancisco);

        filtered = manager.filter(7.0, 8.5);
        assertEquals(1, filtered.size());

        // including all earthquakes
        filtered = manager.filter(5.0, 9.0);
        assertEquals(4, filtered.size());

        // including no earthquakes
        filtered = manager.filter(9.1, 10.0);
        assertEquals(0, filtered.size());
    }
}