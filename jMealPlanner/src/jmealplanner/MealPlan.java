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
    //List<Recipe> breakfast;
    //List<Recipe> lunch;
    //List<Recipe> Dinner;
    
    public static Connection conn = null;
    public static OraclePreparedStatement pst = null;
    public static OracleResultSet rs = null;
    
    MealPlan(int dayOfWeek, int breakfast, int lunch, int dinner) {
        
        this.dayOfWeek = dayOfWeek;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        
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
        return String.format("dayOfWeek: %-30s Breakfast: %-30s Lunch: %-30s Dinner: %-30s", tempDay, breakfast, lunch, dinner);
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
                
                MealPlan fd = new MealPlan(day, breakf, lun, din);
                
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
}
