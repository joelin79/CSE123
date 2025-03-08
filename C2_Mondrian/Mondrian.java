// C2: Mondrian
// Name: Joe Lin
// Date: Feb 18, 2025
// CSE 123 BK
// TA: Benoit Le

import java.awt.*;

/**
 * Generates abstract artwork in the style of Piet Mondrian.
 * The generated artwork consists of random colored rectangular regions separated by black borders.
 * Supports basic and complex mode: with only random primary colors and with hued version of
 * colors respectively.
 * Each artwork will be random and unique compositions.
 */
public class Mondrian {
    public static final int MIN_CANVAS_DIMENSION = 300;
    private static final int MAX_COLOR_VALUE = 255;
    private static final int MIN_SECTION_DIMENSION = 10;
    private static final int MIN_REGION_DIVISION_FACTOR = 4;

    /**
     * - Generates a Mondrian-style painting by recursively dividing an image into rectangular
     *   sections, applying random colors, and ensuring black borders between sections.
     * - Each section is divided only if its width and height are at least 1/4 of the full
     *   canvas dimensions.
     * - Division points are randomly chosen but ensure that each resulting section is at least
     *   10 pixels wide and 10 pixels tall.
     * - The final regions are filled with red, yellow, cyan, or white, leaving a one-pixel
     *   black border around each section.
     *
     * @param pixels The 2D array of Colors representing the image.
     * @throws IllegalArgumentException if the pixels array is null or smaller than 300x300.
     */
    public void paintBasicMondrian(Color[][] pixels) {
        if(pixels == null || pixels.length < MIN_CANVAS_DIMENSION || pixels[0].length < MIN_CANVAS_DIMENSION){
            throw new IllegalArgumentException();
        }
        int width = pixels[0].length;
        int height = pixels.length;
        fill(pixels,0, pixels[0].length, 0, pixels.length);
        paintBasicMondrian(pixels, 0, width, 0, height, width, height);
    }

    /**
     * - Recursively partitions the given region into smaller sections while ensuring
     *   a minimum section size of 10x10 pixels.
     * - The region is split along randomly chosen vertical and/or horizontal lines,
     *   but only if the region's width and height are at least 1/4 of the full canvas size.
     * - Each division creates up to four subregions, which are then recursively processed.
     * - If only one dimension qualifies for division, a single split is made.
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

        if(w >= width/MIN_REGION_DIVISION_FACTOR && h >= height/MIN_REGION_DIVISION_FACTOR){
            //rand v and h boarder
            int randX = (int)(Math.random() * (w - MIN_SECTION_DIMENSION * 2)) + (x1 + MIN_SECTION_DIMENSION); // domain within 10px from boarder
            int randY = (int)(Math.random() * (h - MIN_SECTION_DIMENSION * 2)) + (y1 + MIN_SECTION_DIMENSION);

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

        } else if(w >= width/MIN_REGION_DIVISION_FACTOR){
            //rand v boarder
            int randX = (int)(Math.random() * (w - MIN_SECTION_DIMENSION * 2)) + (x1 + MIN_SECTION_DIMENSION); // domain within 10px from boarder

            // fill
            fill(pixels, x1, randX, y1, y2);
            fill(pixels, randX, x2, y1, y2);

            // recursion
            paintBasicMondrian(pixels, x1, randX, y1, y2, width, height);
            paintBasicMondrian(pixels, randX, x2, y1, y2, width, height);

        } else if(h >= height/MIN_REGION_DIVISION_FACTOR){
            //rand h boarder
            int randY = (int)(Math.random() * (h - MIN_SECTION_DIMENSION * 2)) + (y1 + MIN_SECTION_DIMENSION);

            // fill
            fill(pixels, x1, x2, y1, randY);
            fill(pixels, x1, x2, randY, y2);

            // recursion
            paintBasicMondrian(pixels, x1, x2, y1, randY, width, height);
            paintBasicMondrian(pixels, x1, x2, randY, y2, width, height);
        }
    }

    /**
     * - Fills a section of the image with a randomly selected color from red, yellow, cyan, or white.
     * - Every section will have 1px black border around the section.
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
        createBox(pixels, x1, x2, y1, y2, fillColor);
    }

    /**
     * - Generates a Mondrian-style painting where color selection is influenced by
     *   the section’s position on the canvas.
     * - Uses random colors (cyan, red, white, yellow), modifying their hues based on
     *   spatial placement (more center more shifted hue).
     * - Generates 1px black borders between sections.
     *
     * @param pixels The 2D array of Colors representing the image.
     * @throws IllegalArgumentException if the pixels array is null or smaller than 300x300.
     */
    public void paintComplexMondrian(Color[][] pixels) {
        if(pixels == null || pixels.length < MIN_CANVAS_DIMENSION || pixels[0].length < MIN_CANVAS_DIMENSION){
            throw new IllegalArgumentException();
        }
        int width = pixels[0].length;
        int height = pixels.length;
        fill(pixels,0, pixels[0].length, 0, pixels.length);
        paintComplexMondrian(pixels, 0, width, 0, height, width, height);
    }

    /**
     * - Recursively partitions the canvas and fills sections with a color whose hue
     *   is adjusted based on the section’s position.
     * - Ensures that all sections maintain a minimum size of 10x10 pixels.
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
        if(pixels == null || pixels.length < MIN_CANVAS_DIMENSION || pixels[0].length < MIN_CANVAS_DIMENSION){
            throw new IllegalArgumentException();
        }
        int w = x2 - x1;    // w of current region
        int h = y2 - y1;    // h of cur

        if(w >= width/MIN_REGION_DIVISION_FACTOR && h >= height/MIN_REGION_DIVISION_FACTOR){
            //rand v and h boarder
            int randX = (int)(Math.random() * (w - MIN_SECTION_DIMENSION * 2)) + (x1 + MIN_SECTION_DIMENSION); // domain within 10px from boarder
            int randY = (int)(Math.random() * (h - MIN_SECTION_DIMENSION * 2)) + (y1 + MIN_SECTION_DIMENSION);

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

        } else if(w >= width/MIN_REGION_DIVISION_FACTOR){
            //rand v boarder
            int randX = (int)(Math.random() * (w - MIN_SECTION_DIMENSION * 2)) + (x1 + MIN_SECTION_DIMENSION); // domain within 10px from boarder

            // fill
            fillHue(pixels, x1, randX, y1, y2, width, height);
            fillHue(pixels, randX, x2, y1, y2, width, height);

            // recursion
            paintComplexMondrian(pixels, x1, randX, y1, y2, width, height);
            paintComplexMondrian(pixels, randX, x2, y1, y2, width, height);

        } else if(h >= height/MIN_REGION_DIVISION_FACTOR){
            //rand h boarder
            int randY = (int)(Math.random() * (h - MIN_SECTION_DIMENSION * 2)) + (y1 + MIN_SECTION_DIMENSION);

            // fill
            fillHue(pixels, x1, x2, y1, randY, width, height);
            fillHue(pixels, x1, x2, randY, y2, width, height);

            // recursion
            paintComplexMondrian(pixels, x1, x2, y1, randY, width, height);
            paintComplexMondrian(pixels, x1, x2, randY, y2, width, height);
        }

    }

    /**
     * - Fills a rectangular section of the image with a color that is adjusted based on its
     *   position in the canvas.
     * - A black border is maintained around the section for separation.
     * - The closer the section is to the center, the higher probability of its hue being adjusted.
     * - The base color is chosen randomly from red, yellow, cyan, and white, but its hue is
     *   adjusted using hueColor() to create variation across the image (more center more hue).
     *
     * @param pixels The pixel array.
     * @param x1 The left boundary.
     * @param x2 The right boundary.
     * @param y1 The top boundary.
     * @param y2 The bottom boundary.
     * @param width The overall width of the image.
     * @param height The overall height of the image.
     */
    private static void fillHue(Color[][] pixels, int x1, int x2, int y1, int y2,
                                int width, int height){
        Color[] randColors = {Color.WHITE, Color.CYAN, Color.RED, Color.YELLOW};
        Color fillColor = randColors[(int)(Math.random() * 4)];

        double x = (x1 + x2) / 2;
        double y = (y1 + y2) / 2;
        double distanceToBorder = Math.min(Math.min(x, width - x), Math.min(y, height - y));
        double maxDistance = Math.min(width, height) / 2.0;

        double hueProbability = distanceToBorder / maxDistance; // More center = higher p
        Color adjustedColor = (Math.random() < hueProbability) ?
                hueColor(fillColor, (x1+x2)/2, (y1+y2)/2, width, height) : fillColor;

        createBox(pixels, x1, x2, y1, y2, adjustedColor);
    }


    /**
     * - Draws a rectangular box on a 2D array of pixels with a specified color for the interior
     * and black for the 1px border.
     *
     * @param pixels The 2D array representing the pixel grid.
     * @param x1 The starting x-coordinate of the box (inclusive).
     * @param x2 The ending x-coordinate of the box (exclusive).
     * @param y1 The starting y-coordinate of the box (inclusive).
     * @param y2 The ending y-coordinate of the box (exclusive).
     * @param color The fill color for the interior of the box.
     */
    private static void createBox(Color[][] pixels, int x1, int x2, int y1, int y2, Color color) {
        for(int y = y1; y < y2; y++){
            for(int x = x1; x < x2; x++){
                pixels[y][x] = (x == x1 || x == x2-1 || y == y1 || y == y2-1) ? Color.BLACK : color;
            }
        }
    }

    /**
     * - Adjusts the hue of a selected color based on the region's position on the canvas.
     * The more center the region is, the more shifted the hue is.
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

        int huedR = (int) Math.min(MAX_COLOR_VALUE, (r * invFactor + g * factor));
        int huedG = (int) Math.min(MAX_COLOR_VALUE, (g * invFactor + b * factor));
        int huedB = (int) Math.min(MAX_COLOR_VALUE, (b * invFactor + r * factor));

        return new Color(huedR, huedG, huedB);
    }
}
