import java.awt.*;

public class Client {
    public static void main(String[] args) {
        // 1. Create a new picture sized 400 x 400 pixels
        Color[][] colors = new Color[400][400];
        Picture picture = new Picture(400, 400);

        // 2. Call divide divide canvas
        divideCanvas(colors, 100);

        // 3. Save the image to display it
        picture.setPixels(colors);
        picture.save("image.jpg");
    }

    // TODO: Implement divideCanvas below using your 'fill' implementation
    public static void divideCanvas(Color[][] pixels, int n){
        fill(pixels, 0, pixels[0].length, 0, pixels.length);
        devideCanvas(pixels, n, 0, pixels[0].length, 0, pixels.length);
    }

    public static void devideCanvas(Color[][] pixels, int n, int x1, int x2, int y1, int y2){
        if (n == 0) return;
        int midY = (y1 + y2) / 2;
        int midX = (x1 + x2) / 2;

        // fill first
        fill(pixels, x1, midX, y1, midY);
        fill(pixels, midX, x2, y1, midY);
        fill(pixels, x1, midX, midY, y2);
        fill(pixels, midX, x2, midY, y2);

        // devide inner
        devideCanvas(pixels, n-1, x1, midX, y1, midY);
        devideCanvas(pixels, n-1, midX, x2, y1, midY);
        devideCanvas(pixels, n-1, x1, midX, midY, y2);
        devideCanvas(pixels, n-1, midX, x2, midY, y2);
    }

    // TODO: Paste in 'fill' from the previous slide
    public static void fill(Color[][] pixels, int x1, int x2, int y1, int y2){
        for(int y = y1; y < y2; y++){
            for(int x = x1; x < x2; x++){
                pixels[y][x] = (x==x1 || x==x2-1 || y==y1 || y==y2-1) ? Color.BLACK : Color.WHITE;
            }
        }
    }
}
