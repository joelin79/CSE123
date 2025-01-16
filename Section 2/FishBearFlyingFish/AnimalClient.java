import java.util.*;

public class AnimalClient {

    public static void main(String[] args) {
        // It should be possible to put FishBear and FlyingFish in
        // a List<Animal> together!
        List<Animal> animals = new ArrayList<>();
        Animal flyingFish = new FlyingFish(1);
        Animal fishBear = new FishBear(10);
        animals.add(flyingFish);
        animals.add(fishBear);
        for (Animal a : animals) {
            System.out.println(a.makeSound());
        }

        // Uncomment these line chunks one by one and see what happens!

        // flyingFish.addPredators(fishBear);
        // ((FlyingFish) flyingFish).addPredators(fishBear);
        // ((FlyingFish) fishBear).addPredators(fishBear);

        // System.out.println(flyingFish.getSpecies());

        // System.out.println(flyingFish); // Calls the toString() method on flyingFish.

        // FlyingFish f = new FlyingFish(0);
        // ((FishBear) f).getAge();
    }
}
