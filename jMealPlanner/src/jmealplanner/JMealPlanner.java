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


public class JMealPlanner {

    /**
     * @param args the command line arguments
     */
    
    static ArrayList<Food> fridge; 
    static ArrayList<Food> foodList; 
    
    public static void main(String[] args) 
    {
        FrontEnd fe = new FrontEnd();
        fe.setVisible(true);
        
    }
    
}
