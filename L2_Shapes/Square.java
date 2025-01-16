public class Square extends Shape {
    private double sideLength;
    
    public Square(String color, double sideLength) {
        super(color);
        this.sideLength = sideLength;
    }
    
    public double getArea() {
        return sideLength * sideLength;
    }
    
    public double getDiagonal() {
        return Math.sqrt(2 * sideLength * sideLength);
    }

    public String getName(){
        return "Square";
    }

    public boolean isEmpty(){
        if(getArea() == 0){
            return true;
        }
        return false;
    }
}