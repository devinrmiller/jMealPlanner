/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmealplanner;

/**
 *
 * @author devin
 */
public class Food {
    
    private final String foodID;
    private String category;
    private int calories;
    private int carbs;
    private int protein;
    private int fat;
    private String name;
    private int quantity;
    private String quantityMeasurement;
    
    Food(String in_foodID, String in_category, int in_calories, int in_carbs,
            int in_protein, int in_fat, String in_name, int in_quantity,
            String in_quantityMeasurement) {
        
        this.foodID = in_foodID;
        this.category = in_category;
        this.calories = in_calories;
        this.carbs = in_carbs;
        this.protein = in_protein;
        this.fat = in_fat;
        this.name = in_name;
        this.quantity = in_quantity;
        this.quantityMeasurement = in_quantityMeasurement;
        
    }
    
   String getFoodID() {
       return this.foodID;
   }
   
   String getCategory() {
       return this.category;
   }
   
   int getCalories() {
       return this.calories;
   }
   
   int getCarbs() {
       return this.carbs;
   }
   
   int getProtein() {
       return this.protein;
   }
   
   int getFat() {
       return this.fat;
   }
   
   String getName() {
       return this.name;
   }
   
   int getQuantity() {
       return this.quantity;
   }
    
   String getQuantityMeasurment() {
       return this.quantityMeasurement;
   }
   
   void setCategory(String newCategory) {
       this.category = newCategory;
   }
   
   void setCalories (int newCalories) {
       this.calories = newCalories;
   }
   
   void setCarbs(int newCarbs) {
       this.carbs = newCarbs;
   }
   
   void setProtein(int newProtein) {
       this.protein = newProtein;
   }
   
   void setFat(int newFat) {
       this.fat = newFat;
   }
   
   void setName(String newName) {
       this.name = newName;
   }
   
   void setQuantity(int newQuantity) {
       this.quantity = newQuantity;
   }
   
   void setQuantityMeasurement(String newQuantityMeasurement) {
       this.quantityMeasurement = newQuantityMeasurement;
   }
   
}
