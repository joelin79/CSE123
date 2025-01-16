public class Circle extends Shape {
    private double radius;
    
    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }
    
    public double getArea() {
        return Math.PI * radius * radius;
    }
    
    public double getCircumference() {
        return 2 * Math.PI * radius;
    }

    public String getName(){
        return "Circle";
    }
    public boolean isEmpty(){
        if(getArea() == 0){
            return true;
        }
        return false;
    }
}