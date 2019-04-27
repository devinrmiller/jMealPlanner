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
public class Recipe {
    
    private final String recID;
    private String category;
    private String instructions;
    private int servingsMade;
    private int servingsRemain;
    // private List<Map<Food, String>> ingredients;
    
    Recipe(String in_recID, String in_category, String in_instructions,
            int in_servingsMade, int in_servingsRemain) {
        
        this.recID = in_recID;
        this.category = in_category;
        this.instructions = in_instructions;
        this.servingsMade = in_servingsMade;
        this.servingsRemain = in_servingsRemain;
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
    
    int getServingsMade() {
        return this.servingsMade;
    }
    
    int getServingsRemain() {
        return this.servingsRemain;
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
        if(newServingsMade >= 1)
            this.servingsMade = newServingsMade;
    }
    
    void addServingsRemain(int prepared) {
        this.servingsRemain += prepared;
    }
    
    void subServingsRemain(int consumed) {
        if(this.servingsRemain - consumed >= 0)
            this.servingsRemain -= consumed;
        else
            this.servingsRemain = 0;
    }
    
    // addIngredient
    
    // removeIngredient
    
}
