import java.util.*;

public class GardenClient {

    public static void main(String[] args) {
        Plant plant = new Plant();
        Plant flower = new Flower();
        Plant rose = new Rose();

        // Notice how Plant, Flower, Rose can all be put in a List<Plant>!
        List<Plant> garden = new ArrayList<>();  
        garden.add(plant);
        garden.add(flower);
        garden.add(rose);
        // write testing code here!
    }
}
