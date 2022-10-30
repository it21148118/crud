package com.codingwithzo.realtimedbaddreadupdatedelete;

public class FoodItem {

    String foodID;
    String foodName;
    String foodPrice;
    String foodDescription;

    public FoodItem() {
    }

    public FoodItem(String foodID, String foodName, String foodPrice, String foodDescription) {
        this.foodID = foodID;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodDescription = foodDescription;
    }

    public String getFoodID() {
        return foodID;
    }

    public void setFoodID(String foodID) {
        this.foodID = foodID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }
}