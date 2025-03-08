// P2: Disaster Relief
// Name: Joe Lin
// Date: Feb 26, 2025
// CSE 123 BK
// TA: Benoit Le

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

/**
* This class is for JUnit testing the findPath method in the Client class.
*/
public class Testing {

    @Test
    @DisplayName("STUDENT TEST - Case #1")
    public void firstCaseTest() {
        List<Region> sites = new ArrayList<>();
        Region r1 = new Region("Region #1", 500);
        Region r2 = new Region("Region #2", 700);
        Region r3 = new Region("Region #3", 900);
        Region r4 = new Region("Region #4", 400);
        Region r5 = new Region("Region #5", 300);
        Region r6 = new Region("Region #6", 800);

        r1.addConnection(r2, 2000);
        r1.addConnection(r4, 1500);
        r1.addConnection(r5, 1800);
        r2.addConnection(r1, 2000);
        r2.addConnection(r3, 1500);
        r2.addConnection(r4, 500);
        r2.addConnection(r5, 700);
        r3.addConnection(r2, 1500);
        r4.addConnection(r1, 1500);
        r4.addConnection(r2, 500);
        r4.addConnection(r5, 1400);
        r4.addConnection(r6, 200);
        r5.addConnection(r1, 1800);
        r5.addConnection(r2, 700);
        r5.addConnection(r4, 1400);
        r6.addConnection(r4, 200);

        sites.addAll(Arrays.asList(r1, r2, r3, r4, r5, r6));
        Path result = Client.findPath(sites);

        assertNotNull(result, "Path should not be null");
        assertEquals(5, result.getRegions().size(), "Expected path size is incorrect");
    }

    @Test
    @DisplayName("STUDENT TEST - Case #2")
    public void secondCaseTest() {
        List<Region> sites = new ArrayList<>();
        Region r1 = new Region("Region #1", 1200);
        Region r2 = new Region("Region #2", 9000);
        Region r3 = new Region("Region #3", 4500);
        Region r4 = new Region("Region #4", 4600);
        Region r5 = new Region("Region #5", 1300);
        Region r6 = new Region("Region #6", 7800);
        Region r7 = new Region("Region #7", 2400);

        r1.addConnection(r2, 2900);
        r1.addConnection(r4, 2400);
        r2.addConnection(r1, 2900);
        r2.addConnection(r3, 1600);
        r2.addConnection(r4, 1300);
        r2.addConnection(r5, 3100);
        r3.addConnection(r2, 1600);
        r3.addConnection(r5, 900);
        r4.addConnection(r1, 2400);
        r4.addConnection(r2, 1300);
        r4.addConnection(r6, 1700);
        r4.addConnection(r7, 1200);
        r5.addConnection(r2, 3100);
        r5.addConnection(r3, 900);
        r6.addConnection(r4, 1700);
        r6.addConnection(r7, 600);
        r7.addConnection(r4, 1200);
        r7.addConnection(r6, 600);

        sites.addAll(Arrays.asList(r1, r2, r3, r4, r5, r6, r7));
        Path result = Client.findPath(sites);

        assertTrue(result.totalPeople() >= 20000, "People helped should be at least 20000");
    }

    @Test
    @DisplayName("STUDENT TEST - DIY")
    public void diyTest() {
        List<Region> sites = new ArrayList<>();
        Region r1 = new Region("Region #1", 1000);
        Region r2 = new Region("Region #2", 500);
        Region r3 = new Region("Region #3", 800);
        Region r4 = new Region("Region #4", 1200);

        r1.addConnection(r2, 1);
        r2.addConnection(r3, 2);
        r3.addConnection(r4, 3);
        r4.addConnection(r1, 4);

        sites.addAll(Arrays.asList(r1, r2, r3, r4));
        Path result = Client.findPath(sites);
        assertTrue(result.totalPeople() >= 3500, "Expected at least 3500 people helped");
    }

}
