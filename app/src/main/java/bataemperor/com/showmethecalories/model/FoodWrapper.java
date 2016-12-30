package bataemperor.com.showmethecalories.model;

/**
 * Created by aleksandar on 30.12.16..
 */

public class FoodWrapper {
    private String food;
    private String calorie;

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public FoodWrapper() {
    }

    public FoodWrapper(String food, String calorie) {
        this.food = food;
        this.calorie = calorie;
    }
}
