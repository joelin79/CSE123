package FishBearFlyingFish;

// TODO: Implement Animal here!
public abstract class Animal {
    private String species;
    private String favoriteFood;
    private int age;

    public Animal(String species, String favoriteFood, int age) {
        this.species = species;
        this.favoriteFood = favoriteFood;
        this.age = age;
    }

    public abstract String makeSound();

    public abstract void pet();

    public abstract String getSpecies();

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
