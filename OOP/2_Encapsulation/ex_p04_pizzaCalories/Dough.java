package pizzaCalories;

public class Dough {
    private String flourType;
    private String bakingTechnique;
    private double weight;

    public Dough(String flourType, String bakingTechnique, double weight) {
        setFlourType(flourType);
        setBakingTechnique(bakingTechnique);
        setWeight(weight);
    }

    private void setFlourType(String flourType) {
        if (!flourType.equals("White") && !flourType.equals("Wholegrain")){
            throw new IllegalArgumentException("Invalid type of dough.");
        }

        this.flourType = flourType;
    }

    private void setBakingTechnique(String bakingTechnique) {
        if (!bakingTechnique.equals("Crispy") && !bakingTechnique.equals("Chewy")
        && !bakingTechnique.equals("Homemade")){
            throw new IllegalArgumentException("Invalid type of dough.");
        }

        this.bakingTechnique = bakingTechnique;
    }

    private void setWeight(double weight) {
        if (weight < 1 || weight > 200){
            throw new IllegalArgumentException("Dough weight should be in the range [1..200].");
        }

        this.weight = weight;
    }

    public double calculateCalories(){
        return 2 * weight * getTypeModifier() * getTechniqueModifier();
    }

    private double getTypeModifier(){
        switch (flourType){
            case "White": return 1.5;
            case "Wholegrain": return 1.0;
        }
        return 0;
    }

    private double getTechniqueModifier(){
        switch (bakingTechnique){
            case "Crispy": return 0.9;
            case "Chewy": return 1.1;
            case "Homemade": return 1.0;
        }
        return 0;
    }
}
