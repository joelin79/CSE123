package FishBearFlyingFish;

public class FishBear extends Animal{
    private int fish;
    private String species;
    private String favoriteFood;
    private int age;

    public FishBear(int age, int fish) {
        super("FishBear","fish", age);
        this.fish = fish;
    }

    public FishBear(int age) {
        this(age, 9);
    }

    public String makeSound() {
        return "FishBear is " + age + " years old";
    }

    public void pet() {
        System.out.println("Here's a fish, FishBear!");
        fish++;
    }

    public String getSpecies() {
        return species;
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
