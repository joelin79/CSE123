public class Client {
    public static void main(String[] args) {
        Circle c = new Circle("red", 10.0);
        Square s = new Square("blue", 4.0);
        System.out.println("c is a Circle color=" + c.getColor() + " area=" + c.getArea());
        System.out.println("s is a Square color=" + s.getColor() + " area=" + s.getArea());
    }
}
