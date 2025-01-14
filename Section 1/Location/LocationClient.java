public class LocationClient {
    public static void main(String[] args) {
        Location northPole = new Location("Santa's house", 90, 135);
        Location southPole = new Location("Antisanta's house", -90, 45);
        System.out.println("North pole compared to south pole:");
        int result = northPole.compareTo(southPole);
        System.out.println(result);
    }
}
