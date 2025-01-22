package FishBearFlyingFish;

import java.util.*;

public class FlyingFish extends Animal{
    private Set<Animal> predators;
    private String species;
    private String favoriteFood;
    private int age;

    public FlyingFish(int age, Set<Animal> predators) {
        super("FlyingFish","Fish Food", age);
        this.predators = predators;
    }

    public FlyingFish(int age) {
        this(age, new HashSet<Animal>());
    }

    public String makeSound() {
        return "Flop " + this.favoriteFood;
    }

    public void pet() {
        System.out.print("You petted a FlyingFish with predators: ");
        System.out.println(predators.toString());
    }

    public void addPredators(Animal predator) {
        if (predator == null) {
            throw new IllegalArgumentException();
        }
        predators.add(predator);
    }
    
    public String getSpecies() {
        return "The magnificent" + species;
    }

    public String getFavoriteFood() {
        return favoriteFood;
    }

    public int getAge() {
        return age;
    }

    public String toString() {
        return "Species: " + getSpecies() + ". "+ makeSound() + "!";
    }
}
