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
public class MealPlan {
    
    int dayOfWeek;
    int breakfast;
    int lunch;
    int dinner;
    String breakConv;
    String lunchConv;
    String dinnerConv;
    //List<Recipe> breakfast;
    //List<Recipe> lunch;
    //List<Recipe> Dinner;
    
    public static Connection conn = null;
    public static OraclePreparedStatement pst = null;
    public static OracleResultSet rs = null;
    
    MealPlan(int dayOfWeek, int breakfast, int lunch, int dinner, String breakConv, String lunchConv, String dinnerConv) {
        
        this.dayOfWeek = dayOfWeek;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        this.breakConv = breakConv;
        this.lunchConv = lunchConv;
        this.dinnerConv = dinnerConv;
        
    }
    
    int getDayOfWeek() {
        return this.dayOfWeek;
    }

    int getBreakfast() {
        return this.breakfast;
    }

    int getLunch() {
        return this.lunch;
    }

    int getDinner() {
        return this.dinner;
    }
    
    String getBreakConv(){
        return this.breakConv;
    }
    
    String getLunchConv(){
        return this.lunchConv;
    }
    
    String getDinnerConv(){
        return this.dinnerConv;
    }
    
    void setDayOfWeek(int dayOfWeek){
        this.dayOfWeek = dayOfWeek;
    }
    
    void setBreakast(int breakfast){
        this.breakfast = breakfast;
    }
    
    void setLunch(int lunch){
        this.lunch = lunch;
    }
    
    void setDinner(int dinner){
        this.dinner = dinner;
    }
    
    void setBreakConv(String breakConv){
        this.breakConv = breakConv;
    }
    
    void setLunchConv(String lunchConv){
        this.lunchConv = lunchConv;
    }
    
    void setDinnerConv(String dinnerConv){
        this.dinnerConv = dinnerConv;
    }
    
    public String toString() {
        String tempDay = "";
        if(dayOfWeek == 1)
        {
            tempDay = "Sunday";
        }
        else if(dayOfWeek == 2)
        {
            tempDay = "Monday";
        }
        else if(dayOfWeek == 3)
        {
            tempDay = "Tuesday";
        }
        else if(dayOfWeek == 4)
        {
            tempDay = "Wednesday";
        }
        else if(dayOfWeek == 5)
        {
            tempDay = "Thursday";
        }
        else if(dayOfWeek == 6)
        {
            tempDay = "Friday";
        }
        else if(dayOfWeek == 7)
        {
            tempDay = "Saturday";
        }
        return String.format("dayOfWeek: %-30s Breakfast: %-30s Lunch: %-30s Dinner: %-30s", tempDay, breakConv, lunchConv, dinnerConv);
    }
    
    public static ArrayList<MealPlan> initilizeMealList() 
    {
        //initilize connection variables
        String sqlStatement = "";
        ArrayList<MealPlan> mealList = new ArrayList<MealPlan>();

        //connect, calling our ConnectDb class
        conn = ConnectDb.setupConnection();
        
        //execute sql commands
        try 
        {
            //select all meal plans
            sqlStatement = "select * from MealPlan";
            

            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);

            rs = (OracleResultSet) pst.executeQuery();
            
            //cycle through data from select statement
            //create objects
            //store in a list
            //populate text field in corresponding jFrame tab
            while(rs.next())
            {
                int day = rs.getInt("dayOfWeek");
                int breakf = rs.getInt("breakfast");
                int lun = rs.getInt("lunch");
                int din = rs.getInt("dinner");
                
                MealPlan fd = new MealPlan(day, breakf, lun, din, "", "", "");
                
                //poplate arraylist with objects
                mealList.add(fd);
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
        
        return mealList;
    }
    
    static public int[] getTotalHealth(int ID)
    {
        //initilize connection variables
        String sqlStatement;
        int [] healthFacts = new int[4];
        int Calories = 0;
        int Carbs = 0;
        int Protein = 0;
        int Fat = 0;

        //connect, calling our ConnectDb class
        conn = ConnectDb.setupConnection();
        
        //execute sql commands
        try 
        {
            sqlStatement = "select Ingredients.recID, sum((calories * Ingredients.quantity))as cals , sum(carbs * Ingredients.quantity) as carbs, sum(protein * Ingredients.quantity) as prot, sum(fat * Ingredients.quantity) as fat\n"
                    + "from Ingredients, Food\n"
                    + "where Ingredients.foodID = Food.foodID and Ingredients.recID = ?\n"
                    + "group by Ingredients.recID";
            
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            pst.setInt(1, ID);

            rs = (OracleResultSet) pst.executeQuery();
            
            //cycle through data from select statement
            //store in a list
            //populate text field in corresponding jFrame tab
            while(rs.next())
            {
                Calories += rs.getInt("cals");
                Carbs += rs.getInt("carbs");
                Protein += rs.getInt("prot");
                Fat += rs.getInt("fat");
            }
            
            healthFacts[0] = Calories;
            healthFacts[1] = Carbs;
            healthFacts[2] = Protein;
            healthFacts[3] = Fat;
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
        
        return healthFacts;
    }
    
    static public int deleteMealPlanItem(int ID)
    {
        //initilize connection variables
        String sqlStatement;
        int outcome = 1;

        //connect, calling our ConnectDb class
        conn = ConnectDb.setupConnection();
        
        //execute sql commands
        try 
        {
            sqlStatement = "update MealPlan set breakfast = null, lunch = null, dinner = null where dayOfWeek = ?";
            
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            pst.setInt(1, ID);

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
    
    public static int insertMealPlan(int dayID, int passedID, int whichDay)
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
            if(whichDay == 1)
            {
                sqlStatement = "update MealPlan set breakfast = ? where dayOfWeek = ?";
            }
            if(whichDay == 2)
            {
                sqlStatement = "update MealPlan set lunch = ? where dayOfWeek = ?";
            }
            if(whichDay == 3)
            {
                sqlStatement = "update MealPlan set dinner = ? where dayOfWeek = ?";
            }
            pst = (OraclePreparedStatement) conn.prepareStatement(sqlStatement);
            pst.setInt(1, passedID);
            pst.setInt(2, dayID);  

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
}
