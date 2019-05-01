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

    private String recID;
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

    Recipe(String id, String name, String cat, int serves, int cals, int carbs, int prot, int fat, String instructions) {
        this.recID = id;
        this.name = name;
        this.category = cat;
        this.servingsMade = serves;
        this.cals = cals;
        this.carbs = carbs;
        this.prot = prot;
        this.fat = fat;
        this.instructions = instructions;
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
    
    public String toString(){
       return String.format( "Name: %-50s Category: %-20s", name, category);
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
            sqlStatement = "select Ingredients.recID, recipe.name, category, servingsTotal, sum((calories * Ingredients.quantity))as cals , sum(carbs * Ingredients.quantity) as carbs, sum(protein * Ingredients.quantity) as prot, sum(fat * Ingredients.quantity) as fat, Recipe.instructions\n"
                    + "from Ingredients, Food, recipe\n"
                    + "where Ingredients.foodID = Food.foodID and recipe.recID = Ingredients.recID\n"
                    + "group by Ingredients.recID, category, servingsTotal, recipe.name, recipe.instructions";
            
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
                String instruc = rs.getString("instructions");
                
                Recipe re = new Recipe(id, name, cat, serves, cals, carbs, prot, fat, instruc);
                
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
    
    public static int deleteRecipe(String passedID)
    {
        //initilize connection variables
        //outcome is used rather than return in catch so that the connection is still
        //closed by allowing the try to reach finally
        String sqlStatement = "";
        int outcome = 1;

        //connect, calling our ConnectDb class
        conn = ConnectDb.setupConnection();
        
        //execute sql commands
        try 
        {
            sqlStatement = "delete from recipe where recID = ?";
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            pst.setInt(1, Integer.valueOf(passedID));

            //execute the query
            rs = (OracleResultSet) pst.executeQuery();
            if(rs.next() == false)
            {
                outcome = 0;
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
        
        return outcome;
    }

    public static int insertRecipe(String[] insertRecipe)
    {
        //initilize connection variables
        //outcome is used rather than return in catch so that the connection is still
        //closed by allowing the try to reach finally
        String sqlStatement = "";
        int outcome = 1;

        //connect, calling our ConnectDb class
        conn = ConnectDb.setupConnection();
        
        //execute sql commands
        try 
        {
            sqlStatement = "insert into Recipe (name, category, instructions, servingsTotal, servingsRemain) values (?, ?, ?, ?, ?)";
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            pst.setString(1, String.valueOf(insertRecipe[0]));  
            pst.setString(2, String.valueOf(insertRecipe[1])); 
            pst.setString(3, String.valueOf(insertRecipe[2])); 
            pst.setInt(4, Integer.valueOf(insertRecipe[3])); 
            pst.setInt(5, 0); 

            //execute the query
            rs = (OracleResultSet) pst.executeQuery();
            if(rs.next() == false)
            {
                outcome = 0;
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
        
        return outcome;
    }
    
    // addIngredient
    public static void insertIngredient(int recipeID, int foodID, int foodQuant, String foodQuantMeasurement)
    {
        //initilize connection variables
        //outcome is used rather than return in catch so that the connection is still
        //closed by allowing the try to reach finally
        String sqlStatement = "";

        //connect, calling our ConnectDb class
        conn = ConnectDb.setupConnection();
        
        //execute sql commands
        try 
        {
            sqlStatement = "insert into Ingredients (recID, foodID, quantity, quantityMeasurement) values (?, ?, ?, ?)";
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);              
            pst.setInt(1, recipeID);                         
            pst.setInt(2, foodID);                         
            pst.setInt(3, foodQuant);
            pst.setString(4, foodQuantMeasurement);

            //execute the query
            rs = (OracleResultSet) pst.executeQuery();
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
    }
    
    //get newly generate recipe ID
    public static String getNewRecID(String recName, String recCat, String recInstructions, int recServings)
    {
        //initilize connection variables
        String sqlStatement;
        int recipeID = 0;

        //connect, calling our ConnectDb class
        conn = ConnectDb.setupConnection();
        
        //execute sql commands
        try 
        {
            sqlStatement = "select * from recipe where (name = ? and category = ? and instructions = ? and servingsTotal = ?)";
            
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            pst.setString(1, recName);
            pst.setString(2, recCat);
            pst.setString(3, recInstructions);
            pst.setInt(4, recServings);

            rs = (OracleResultSet) pst.executeQuery();
            
            //cycle through data from select statement
            if (!rs.isBeforeFirst()) {
                System.out.println("No data!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
            while(rs.next())
            {
                recipeID = rs.getInt("recID");
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
        
        return String.valueOf(recipeID);
    }
}
