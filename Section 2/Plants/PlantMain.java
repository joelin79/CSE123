public class PlantMain {
    public static void main(String[] args) {
        // Create instances of Flower and Tree
        Plant flower = new Flower("Tulip", "Red");
        Plant tree = new Tree("Oak", "Green", 10);

        // Call common method
        flower.water();
        tree.water();

        // Call specific methods
        ((Flower) flower).bloom();
        ((Tree) tree).grow();

        // Call displayInfo() for both
        flower.displayInfo();
        tree.displayInfo();
    }
}