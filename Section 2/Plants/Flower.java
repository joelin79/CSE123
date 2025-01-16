public class Flower {
    private String name;
    private String color;

    public Flower(String name, String color) {
        this.name = name;
        this.color = color;
        this.type = type;
    }

    public void displayInfo() {
        System.out.println("Flower: " + name + ", Color: " + color);
    }

    public void water() {
        System.out.println(name + " is being watered.");
    }

    public void bloom() {
        System.out.println(name + " is blooming!");
    }
}
