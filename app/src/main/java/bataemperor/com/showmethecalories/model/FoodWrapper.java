package bataemperor.com.showmethecalories.model;

/**
 * Created by aleksandar on 30.12.16..
 */

public class FoodWrapper {
    private String food;
    private double calorie;

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public double getCalorie() {
        return calorie;
    }

    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }

    public FoodWrapper() {
    }
}
