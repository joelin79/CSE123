// P2: Disaster Relief
// Name: Joe Lin
// Date: Feb 26, 2025
// CSE 123 BK
// TA: Benoit Le

import java.util.*;

/**
 * The Client class allocates the best rescue path for disaster relief.
 * It generates a scenario with regions, finds an optimal path to maximize the number
 * of people helped while minimizing cost, and displays the results.
 */
public class Client {
    private static final Random RAND = new Random();

    public static void main(String[] args) throws Exception {
        // List<Region> scenario = createRandomScenario(10, 10, 100, 1000, 100000);
        List<Region> scenario = createRandomScenario(5, 100, 1000, 100, 1000);
        System.out.println(scenario);
        
        Path allocation = findPath(scenario);
        if (allocation != null) {
            printResult(allocation);
        } else {
            System.out.println("No valid path found. :-(");
        }
    }

    // TODO implement the findPath method! 
    /**
     * Finds the optimal path through the given list of regions to maximize the number of people
     *  helped.
     * If multiple paths help the most people, the one with the lowest cost is chosen.
     * If there are still multiple valid paths, any of them may be returned.
     * Each region can be visited at most once, and the path must start from the first region in
     *  the list.
     *
     * @param sites The list of regions to consider in the path.
     * @return The optimal path that maximizes the number of people helped and minimizes cost if
     *  needed. Null if the list of regions is empty.
     * @throws IllegalArgumentException If the sites list is null.
     */
    public static Path findPath(List<Region> sites) {
        if(sites == null) {
            throw new IllegalArgumentException();
        }
        if(sites.isEmpty()) {
            return null;
        }

//        return findPath(sites, new Path().extend(sites.remove(0)), new Path());
        Region first = sites.get(0);
        Path initPath = new Path().extend(sites.get(0));
        return findPath(sites, first, new HashSet<>(), initPath, null);
    }

    /**
     * Recursively explores possible paths to find the optimal allocation for disaster relief.
     * It selects regions, tracks visited locations, and evaluates paths based on the number
     * of people helped (most) and total cost (least).
     *
     * @param sites The list of available regions.
     * @param currRegion The current region being visited.
     * @param visitedRegions A set of regions that have already been visited.
     * @param path The current path being constructed.
     * @param bestPath The best path found so far.
     * @return The optimal path with the highest number of people helped and the lowest cost.
     */
    private static Path findPath(List<Region> sites, Region currRegion, Set<Region> visitedRegions,
                                 Path path, Path bestPath) {
        // choose
        visitedRegions.add(currRegion);

        // base case
        if (bestPath == null ||
            path.totalPeople() > bestPath.totalPeople() ||
            (path.totalPeople() == bestPath.totalPeople())
                  && (path.totalCost() < bestPath.totalCost())) {
            bestPath = path;
        }

        // explore
        // x = change(x)
        for (Region site : sites) {
            if (!visitedRegions.contains(site) && currRegion.canReach(site)) {
                bestPath = findPath(sites, site, visitedRegions, path.extend(site), bestPath);
            }
        }

        // unchoose
        visitedRegions.remove(currRegion);

        //return
        return bestPath;
    }

    // broken version
//    private static Path findPath(List<Region> sites, Path path, Path bestPath) {
//        if (path.size() > 0 &&
//            (bestPath.size() == 0 ||
//                    path.totalPeople() > bestPath.totalPeople() ||
//                    (path.totalPeople() == bestPath.totalPeople()
//                          && path.totalCost() < bestPath.totalCost()))) {
//            bestPath = path;
//        }
//
//        for(int i = 0; i < sites.size(); i++) {
//            if (path.getEnd().canReach(sites.get(i))) {
//                // Choose
//                Path newPath = path.extend(sites.get(i));
//                List<Region> remainingSites = new ArrayList<>(sites);
//                remainingSites.remove(i);
//
//                // Explore
//                Path candidatePath = findPath(remainingSites, newPath, bestPath);
//
//                // Update better: x = change(x)
//                if (bestPath == null ||
//                        candidatePath.totalPeople() > bestPath.totalPeople() ||
//                        (candidatePath.totalPeople() == bestPath.totalPeople() &&
//                                candidatePath.totalCost() <= bestPath.totalCost())){
//                    bestPath = candidatePath;
//                }
//                // don't need to unchoose
//            }
//        }
//
//        return bestPath;
//    }


    ///////////////////////////////////////////////////////////////////////////
    // PROVIDED HELPER METHODS - **DO NOT MODIFY ANYTHING BELOW THIS LINE!** //
    ///////////////////////////////////////////////////////////////////////////
    
    /**
    * Prints each path in the provided set. Useful for getting a quick overview
    * of all path currently in the system.
    * @param paths Set of paths to print
    */
    public static void printPaths(Set<Path> paths) {
        System.out.println("All Allocations:");
        for (Path a : paths) {
            System.out.println("  " + a);
        }
    }

    /**
    * Prints details about a specific path result, including the total people
    * helped and total cost.
    * @param path The path to print
    */
    public static void printResult(Path path) {
        System.out.println("Result: ");
        List<Region> regions = path.getRegions();
        System.out.print(regions.get(0).getName());
        for (int i = 1; i < regions.size(); i++) {
            System.out.print(" --($" + regions.get(i - 1).getCostTo(regions.get(i)) + ")-> " + regions.get(i).getName());
        }
        System.out.println();
        System.out.println("  People helped: " + path.totalPeople());
        System.out.printf("  Cost: $%.2f\n", path.totalCost());
    }

    /**
    * Creates a scenario with numRegions regions by randomly choosing the population 
    * and travel costs for each region.
    * @param numRegions Number of regions to create
    * @param minPop Minimum population per region
    * @param maxPop Maximum population per region
    * @param minCost Minimum cost of travel between regions
    * @param maxCost Maximum cost of travel between regions
    * @return A list of randomly generated regions
    */
    public static List<Region> createRandomScenario(int numRegions, int minPop, int maxPop,
                                                    double minCost, double maxCost) {
        List<Region> result = new ArrayList<>();

        // ranomly create regions
        for (int i = 0; i < numRegions; i++) {
            // int pop = RAND.nextInt(minPop, maxPop + 1);
            int pop = RAND.nextInt(maxPop - minPop + 1) + minPop;
            result.add(new Region("Region #" + i, pop));
        }

        // randomly create connections between regions
        for (int i = 0; i < numRegions; i++) {
            // int numConnections = RAND.nextInt(1, numLocs - i);
            Region site = result.get(i);
            for (int j = i + 1; j < numRegions; j++) {
                // flip a coin to decide whether or not to add each connection
                if (RAND.nextBoolean()) {
                    Region other = result.get(j);
                    // double cost = round2(RAND.nextDouble(minCostPer, maxCostPer));
                    double cost = round2(RAND.nextDouble() * (maxCost - minCost) + maxCost);
                    site.addConnection(other, cost);
                    other.addConnection(site, cost);
                }
            }
        }

        return result;
    }

    /**
    * Manually creates a simple list of regions to represent a known scenario.
    * @return A simple list of regions
    */
    public static List<Region> createSimpleScenario() {
        List<Region> result = new ArrayList<>();
        Region regionOne = new Region("Region #1", 100);
        Region regionTwo = new Region("Region #2", 50);
        Region regionThree = new Region("Region #3", 100);

        regionOne.addConnection(regionTwo, 200);
        regionOne.addConnection(regionThree, 300);

        regionTwo.addConnection(regionOne, 200);

        regionThree.addConnection(regionOne, 300);
        
        result.add(regionOne);   // Region #1: pop. 100 - [Region #2 (200.0), Region #3 (300.0)]
        result.add(regionTwo);   // Region #2: pop. 50 - [Region #1 (200.0)]
        result.add(regionThree); // Region #3: pop. 100 - [Region #1 (300.0)]

        return result;
    }   

    /**
    * Rounds a number to two decimal places.
    * @param num The number to round
    * @return The number rounded to two decimal places
    */
    private static double round2(double num) {
        return Math.round(num * 100) / 100.0;
    }
}
