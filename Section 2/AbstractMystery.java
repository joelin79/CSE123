// TODO: Identify the errors in the following snippets of code, and 
// suggest any appropriate fixes.


public class AbstractMystery {
    public static void main(String[] args) {
        GymEquipment ge = new GymEquipment("legs");
        GymEquipment bp = new BenchPress(1);
        ge.performExercise(123.4);
        bp.performExercise(432.1);
    }
}

class GymEquipment {
    private String muscleGroup;

    public GymEquipment(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public abstract void performExercise(double weight) {
        System.out.println("Lifting " + weight + " pounds on my " + muscleGroup);
    }

    public abstract void repair(double cost);
}


class BenchPress extends GymEquipment {
    private int reps;

    public BenchPress(int reps) {
        super("chest");
        this.reps = reps;
    }
}
