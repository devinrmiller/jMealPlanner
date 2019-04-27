/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jmealplanner;

import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

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
    
    public static Connection conn = null;
    public static OraclePreparedStatement pst = null;
    public static OracleResultSet rs = null;
    
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
    
    //getters
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
   
   //setters
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
   
   public String toString(){
       return name + " | " + category + " | " + calories + " | " + fat + " | " + carbs + " | " + protein + " | " + quantity;
   }
   
   //method used to create list of foods to populate list on load.
    public static ArrayList<Food> initilizeFoodList(int call) 
    {
        //initilize connection variables
        String sqlStatement = "";
        ArrayList<Food> foodList = new ArrayList<Food>();

        //connect, calling our ConnectDb class
        conn = ConnectDb.setupConnection();
        
        //execute sql commands
        try 
        {
            //select all food CURRENTLY available in fridge
            //meaning do not include what is not readily available
            //0 = Fridge | Otherwise = Food Dict
            if(call == 0)
            {
                sqlStatement = "select * from Food where quantity >= 0";
            }
            else
            {
                sqlStatement = "select * from Food";
            }

            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);

            rs = (OracleResultSet) pst.executeQuery();
            
            //cycle through data from select statement
            //create objects
            //store in a list
            //populate text field in corresponding jFrame tab
            while(rs.next())
            {
                String id = rs.getString("foodID");
                String category = rs.getString("groupType");
                int calories = rs.getInt("calories");
                int carbs = rs.getInt("carbs");
                int protein = rs.getInt("protein");
                int fat = rs.getInt("fat");
                String foodName = rs.getString("name");
                int quant = rs.getInt("quantity");
                String quantMeasure = rs.getString("quantityMeasurement");
                
                Food fd = new Food(id, category, calories, carbs, protein, fat, foodName, quant, quantMeasure);
                
                //poplate arraylist with objects
                foodList.add(fd);
            }
        } 
        catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, e);
        } 
        finally 
        {
            ConnectDb.close(rs);
            ConnectDb.close(pst);
            ConnectDb.close(conn);
        }
        
        return foodList;
    }
   
}
