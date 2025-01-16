public class Tree {
    private String name;
    private String color;
    private int height;

    public Tree(String name, String color, int height) {
        this.name = name;
        this.color = color;
        this.height = height;
    }

    public void displayInfo() {
        System.out.println("Tree: " + name + ", Color: " + color + ", Height: " + height + " meters");
    }

    public void water() {
        System.out.println(name + " is being watered.");
    }

    public void grow() {
        System.out.println(name + " is growing taller!");
    }
}
