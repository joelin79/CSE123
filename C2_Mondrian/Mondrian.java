// C2: Mondrian
// Name: Joe Lin
// Date: Feb 18, 2025
// CSE 123 BK
// TA: Benoit Le

import java.awt.*;

/**
 * Generates a Mondrian-style painting using recursive partitioning.
 */
public class Mondrian {

    /**
     * Paints a basic Mondrian-style pattern on the given pixel array.
     *
     * @param pixels The 2D array of Colors representing the image.
     * @throws IllegalArgumentException if the pixels array is null or smaller than 300x300.
     */
    public void paintBasicMondrian(Color[][] pixels) {
        if(pixels == null || pixels.length < 300 || pixels[0].length < 300){
            throw new IllegalArgumentException();
        }
        int width = pixels[0].length;
        int height = pixels.length;
        fill(pixels,0, pixels[0].length, 0, pixels.length);
        paintBasicMondrian(pixels, 0, width, 0, height, width, height);
    }

    /**
     * Recursively partitions the canvas and fills sections with colors.
     *
     * @param pixels The pixel array representing the image.
     * @param x1 The left boundary of the current region.
     * @param x2 The right boundary of the current region.
     * @param y1 The top boundary of the current region.
     * @param y2 The bottom boundary of the current region.
     * @param width The overall width of the image.
     * @param height The overall height of the image.
     */
    private void paintBasicMondrian(Color[][] pixels, int x1, int x2, int y1, int y2, int width, int height){
        int w = x2 - x1;    // w of current region
        int h = y2 - y1;    // h of cur

        if(w >= width/4 && h >= height/4){
            //rand v and h boarder
            int randX = (int)(Math.random() * (w - 20)) + (x1 + 10); // domain within 10px from boarder
            int randY = (int)(Math.random() * (h - 20)) + (y1 + 10);

            // fill
            fill(pixels, x1, randX, y1, randY);
            fill(pixels, randX, x2, y1, randY);
            fill(pixels, x1, randX, randY, y2);
            fill(pixels, randX, x2, randY, y2);

            // recursion
            paintBasicMondrian(pixels, x1, randX, y1, randY, width, height);
            paintBasicMondrian(pixels, randX, x2, y1, randY, width, height);
            paintBasicMondrian(pixels, x1, randX, randY, y2, width, height);
            paintBasicMondrian(pixels, randX, x2, randY, y2, width, height);

        } else if(w >= width/4){
            //rand v boarder
            int randX = (int)(Math.random() * (w - 20)) + (x1 + 10); // domain within 10px from boarder

            // fill
            fill(pixels, x1, randX, y1, y2);
            fill(pixels, randX, x2, y1, y2);

            // recursion
            paintBasicMondrian(pixels, x1, randX, y1, y2, width, height);
            paintBasicMondrian(pixels, randX, x2, y1, y2, width, height);

        } else if(h >= height/4){
            //rand h boarder
            int randY = (int)(Math.random() * (h - 20)) + (y1 + 10);

            // fill
            fill(pixels, x1, x2, y1, randY);
            fill(pixels, x1, x2, randY, y2);

            // recursion
            paintBasicMondrian(pixels, x1, x2, y1, randY, width, height);
            paintBasicMondrian(pixels, x1, x2, randY, y2, width, height);
        }
    }

    /**
     * Fills a rectangular section of the pixel array with a random color, leaving black borders.
     *
     * @param pixels The pixel array.
     * @param x1 The left boundary.
     * @param x2 The right boundary.
     * @param y1 The top boundary.
     * @param y2 The bottom boundary.
     */
    private static void fill(Color[][] pixels, int x1, int x2, int y1, int y2){
        Color[] randColors = {Color.WHITE, Color.CYAN, Color.RED, Color.YELLOW};
        Color fillColor = randColors[(int)(Math.random() * 4)];
        for(int y = y1; y < y2; y++){
            for(int x = x1; x < x2; x++){
                pixels[y][x] = (x==x1 || x==x2-1 || y==y1 || y==y2-1) ? Color.BLACK : fillColor;
            }
        }
    }

    /**
     * Paints a complex Mondrian-style pattern on the given pixel array.
     *
     * @param pixels The 2D array of Colors representing the image.
     * @throws IllegalArgumentException if the pixels array is null or smaller than 300x300.
     */
    public void paintComplexMondrian(Color[][] pixels) {
        if(pixels == null || pixels.length < 300 || pixels[0].length < 300){
            throw new IllegalArgumentException();
        }
        int width = pixels[0].length;
        int height = pixels.length;
        fill(pixels,0, pixels[0].length, 0, pixels.length);
        paintComplexMondrian(pixels, 0, width, 0, height, width, height);
    }

    /**
     * Recursively partitions the canvas and fills sections with a hue-based coloring scheme.
     *
     * @param pixels The pixel array representing the image.
     * @param x1 The left boundary of the current region.
     * @param x2 The right boundary of the current region.
     * @param y1 The top boundary of the current region.
     * @param y2 The bottom boundary of the current region.
     * @param width The overall width of the image.
     * @param height The overall height of the image.
     */
    private void paintComplexMondrian(Color[][] pixels, int x1, int x2, int y1, int y2, int width, int height){
        if(pixels == null || pixels.length < 300 || pixels[0].length < 300){
            throw new IllegalArgumentException();
        }
        int w = x2 - x1;    // w of current region
        int h = y2 - y1;    // h of cur

        if(w >= width/4 && h >= height/4){
            //rand v and h boarder
            int randX = (int)(Math.random() * (w - 20)) + (x1 + 10); // domain within 10px from boarder
            int randY = (int)(Math.random() * (h - 20)) + (y1 + 10);

            // fill
            fillHue(pixels, x1, randX, y1, randY, width, height);
            fillHue(pixels, randX, x2, y1, randY, width, height);
            fillHue(pixels, x1, randX, randY, y2, width, height);
            fillHue(pixels, randX, x2, randY, y2, width, height);

            // recursion
            paintComplexMondrian(pixels, x1, randX, y1, randY, width, height);
            paintComplexMondrian(pixels, randX, x2, y1, randY, width, height);
            paintComplexMondrian(pixels, x1, randX, randY, y2, width, height);
            paintComplexMondrian(pixels, randX, x2, randY, y2, width, height);

        } else if(w >= width/4){
            //rand v boarder
            int randX = (int)(Math.random() * (w - 20)) + (x1 + 10); // domain within 10px from boarder

            // fill
            fillHue(pixels, x1, randX, y1, y2, width, height);
            fillHue(pixels, randX, x2, y1, y2, width, height);

            // recursion
            paintComplexMondrian(pixels, x1, randX, y1, y2, width, height);
            paintComplexMondrian(pixels, randX, x2, y1, y2, width, height);

        } else if(h >= height/4){
            //rand h boarder
            int randY = (int)(Math.random() * (h - 20)) + (y1 + 10);

            // fill
            fillHue(pixels, x1, x2, y1, randY, width, height);
            fillHue(pixels, x1, x2, randY, y2, width, height);

            // recursion
            paintComplexMondrian(pixels, x1, x2, y1, randY, width, height);
            paintComplexMondrian(pixels, x1, x2, randY, y2, width, height);
        }

    }

    /**
     * Fills a rectangular section of the pixel array with a hue-based coloring scheme.
     *
     * @param pixels The pixel array.
     * @param x1 The left boundary.
     * @param x2 The right boundary.
     * @param y1 The top boundary.
     * @param y2 The bottom boundary.
     * @param width The overall width of the image.
     * @param height The overall height of the image.
     */
    private static void fillHue(Color[][] pixels, int x1, int x2, int y1, int y2, int width, int height){
        Color[] randColors = {Color.WHITE, Color.CYAN, Color.RED, Color.YELLOW};
        Color fillColor = randColors[(int)(Math.random() * 4)];
        Color adjustedColor = hueColor(fillColor, (x1+x2)/2, (y1+y2)/2, width, height);

        for(int y = y1; y < y2; y++){
            for(int x = x1; x < x2; x++){
                pixels[y][x] = (x == x1 || x == x2-1 || y == y1 || y == y2-1) ? Color.BLACK : adjustedColor;
            }
        }
    }

    /**
     * Adjusts the hue of a color based on its distance from the borders of the canvas.
     *
     * @param original The original color.
     * @param x The x-coordinate of the pixel.
     * @param y The y-coordinate of the pixel.
     * @param width The width of the image.
     * @param height The height of the image.
     * @return A new color with adjusted hues.
     */
    private static Color hueColor(Color original, int x, int y, int width, int height) {
        int distanceToBorder = Math.min(Math.min(x, width - x), Math.min(y, height - y));
        double factor = (double)distanceToBorder / Math.max(width, height);
        double invFactor = 1.0 - factor;

        int r = original.getRed();
        int g = original.getGreen();
        int b = original.getBlue();

        int huedR = (int) Math.min(255, (r * invFactor + g * factor));
        int huedG = (int) Math.min(255, (g * invFactor + b * factor));
        int huedB = (int) Math.min(255, (b * invFactor + r * factor));

        return new Color(huedR, huedG, huedB);
    }
}
