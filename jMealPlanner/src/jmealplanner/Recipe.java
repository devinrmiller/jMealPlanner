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
public class Recipe {

    private final String recID;
    private String category;
    private String instructions;
    private int servingsMade;
    private int servingsRemain;
    // private List<Map<Food, String>> ingredients;
    
    private String name;
    private int cals;
    private int carbs;
    private int prot;
    private int fat;
    
    public static Connection conn = null;
    public static OraclePreparedStatement pst = null;
    public static OracleResultSet rs = null;

    Recipe(String in_recID, String in_category, String in_instructions,
            int in_servingsMade, int in_servingsRemain) {

        this.recID = in_recID;
        this.category = in_category;
        this.instructions = in_instructions;
        this.servingsMade = in_servingsMade;
        this.servingsRemain = in_servingsRemain;
    }

    Recipe(String id, String name, String cat, int serves, int cals, int carbs, int prot, int fat) {
        this.recID = id;
        this.name = name;
        this.category = cat;
        this.servingsMade = serves;
        this.cals = cals;
        this.carbs = carbs;
        this.prot = prot;
        this.fat = fat;
                
    }

    String getRecID() {
        return this.recID;
    }

    String getCategory() {
        return this.category;
    }

    String getInstructions() {
        return this.instructions;
    }
    String getName(){
        return this.name;
    }
    int getServingsMade() {
        return this.servingsMade;
    }
    int getCalories() {
        return this.cals;
    }
    int getCarbs() {
        return this.carbs;
    }
    int getProteins() {
        return this.prot;
    }
    int getFats() {
        return this.fat;
    }
    

    // getIngredient
    // getIngredients
    void setCategory(String newCategory) {
        this.category = newCategory;
    }

    void setInstructions(String newInstructions) {
        this.instructions = newInstructions;
    }

    void setServingsMade(int newServingsMade) {
        if (newServingsMade >= 1) {
            this.servingsMade = newServingsMade;
        }
    }

    void addServingsRemain(int prepared) {
        this.servingsRemain += prepared;
    }

    void subServingsRemain(int consumed) {
        if (this.servingsRemain - consumed >= 0) {
            this.servingsRemain -= consumed;
        } else {
            this.servingsRemain = 0;
        }
    }
    public static ArrayList<Recipe> initilizeRecipeList()
    {
         //initilize connection variables
        String sqlStatement;
        ArrayList<Recipe> RecipeList = new ArrayList<>();

        //connect, calling our ConnectDb class
        conn = ConnectDb.setupConnection();
        
        //execute sql commands
        try 
        {
            sqlStatement = "select Ingredients.recID,recipe.name, category, servingsTotal, sum((calories * Ingredients.quantity))as cals , sum(carbs * Ingredients.quantity) as carbs, sum(protein * Ingredients.quantity) as prot, sum(fat * Ingredients.quantity) as fat\n"
                    + "from Ingredients, Food, recipe\n"
                    + "where Ingredients.foodID = Food.foodID and recipe.recID = Ingredients.recID\n"
                    + "group by Ingredients.recID,category, servingsTotal,recipe.name;";
            
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);

            rs = (OracleResultSet) pst.executeQuery();
            
            //cycle through data from select statement
            //create objects
            //store in a list
            //populate text field in corresponding jFrame tab
            while(rs.next())
            {
                String id = rs.getString("recid");
                String name = rs.getString("name");
                String cat = rs.getString("category");
                int serves = rs.getInt("servingstotal");
                int cals = rs.getInt("cals");
                int carbs = rs.getInt("carbs");
                int prot = rs.getInt("prot");
                int fat = rs.getInt("fat");
                
                
                
                Recipe re = new Recipe(id, name, cat, serves, cals, carbs, prot, fat);
                
                //poplate arraylist with objects
                RecipeList.add(re);
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
        
        return RecipeList;
    }

    // addIngredient
    // removeIngredient
}
