public abstract class Shape {
    private String color;
    
    public Shape(String color) {
        this.color = color;
    }
    
    public String getColor() {
        return color;
    }

    public abstract double getArea();
    public abstract String getName();

    public boolean isEmpty(){
        return getArea() == 0;
    }
}