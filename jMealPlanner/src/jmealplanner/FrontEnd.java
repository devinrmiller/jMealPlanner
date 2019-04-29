package jmealplanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author devin
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class FrontEnd extends JFrame {
    
    //generated variables
    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension d = tk.getScreenSize();
    
    //custom variables
    static ArrayList<Food> fridge; 
    static ArrayList<Food> foodList; 
    static ArrayList<Recipe> recipeList;
    static ArrayList<MealPlan> mealList;
    
    /**
     * Creates new form FrontEnd
     */
    
    public FrontEnd() {
        initComponents();
        
        jTextField13.setEditable(false);
        jTextField14.setEditable(false);
        jTextField15.setEditable(false);
        jTextField16.setEditable(false);
        jTextField17.setEditable(false);
        jTextField18.setEditable(false);
        jTextField19.setEditable(false);
        jTextField20.setEditable(false);
        jTextField21.setEditable(false);
        
        //initial data load
        fridge = Food.initilizeFoodList(0);                     //contains list of objects with foods of quantity >=0
        foodList = Food.initilizeFoodList(5);                   //contains list of objects of all foods in database. including negative values
        recipeList = Recipe.initilizeRecipeList();
        mealList = MealPlan.initilizeMealList();
        
        //initially load JLists with data from database
        populateFridge();
        populateFoodDict();
        populateRecipe();
        populateMealPlan();
        
        DefaultListModel modelToPass = (DefaultListModel) jList1.getModel();
        sortJListMealPlan(modelToPass, 1);
        
        /*
        *
        *   Fridge Tab Button Listeners
        *
        */
        // the - button (delete)
        jButton6.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e){
                //variable declaration
                int confirmation = 0; 
                int index = jList3.getSelectedIndex();
                DefaultListModel model = (DefaultListModel) jList3.getModel();
                
                Food item = (Food) jList3.getSelectedValue();
                
                //check if a value is selected
                if (jList3.getSelectedIndex() == -1) 
                {
                    JOptionPane.showMessageDialog(getParent(), "Please select an item!");
                } 
                else 
                {
                    //display a confirmation of Yes | NO | Cancel
                    //0=yes 1=no 2=cancel
                    int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this item?");
                    if (input == 0) 
                    {
                        //call our delete function/query
                        //use confirmation to check if succeeded
                        confirmation = Food.deleteFood(item.getFoodID());
                        //check if succeeded or failed
                        if (confirmation == 0) 
                        {
                            //fail
                            JOptionPane.showMessageDialog(getParent(), "Something went wrong  ):");
                        } 
                        else if (confirmation == 1) 
                        {
                            //succeed. remove item from JList as well
                            JOptionPane.showMessageDialog(getParent(), "Food Deleted Successfully!");
                            model.removeElementAt(index);

                            //repopulate JList using existing query rather than
                            //creating new one to get the ID of newly entered food
                            //and creating an object to add
                            fridge = Food.initilizeFoodList(0);
                            foodList = Food.initilizeFoodList(5);
                            recipeList = Recipe.initilizeRecipeList();

                            populateRecipe();
                            populateFridge();
                            populateFoodDict();
                        }
                    }
                }
            }
        });
        
        //the + button (add/insert)
        jButton5.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e){
                //variable declaration
                JTextField foodName = new JTextField();
                JTextField foodGroup = new JTextField();
                JTextField foodCalories = new JTextField();
                JTextField foodCarbs = new JTextField();
                JTextField foodProtein = new JTextField();
                JTextField foodFat = new JTextField();
                JTextField foodQuant = new JTextField();
                JTextField foodQuantMeasure = new JTextField();
                
                Object[] foodInfo ={
                    "Name: ", foodName,
                    "Food Group: ", foodGroup,
                    "Calories: ", foodCalories,
                    "Carbs: ", foodCarbs,
                    "Protein: ", foodProtein,
                    "Fat: ", foodFat,
                    "Quantity: ", foodQuant,
                    "Measurement Type (oz, Fl.oz, etc.): ", foodQuantMeasure
                };

                //create a new pane to be displayed
                int option = JOptionPane.showConfirmDialog(getParent(), foodInfo, "Enter Information for new Food", JOptionPane.OK_CANCEL_OPTION);
                if(option == JOptionPane.OK_OPTION)
                {
                    //get entered values
                    String value1 = foodName.getText();
                    String value2 = foodGroup.getText();
                    String value3 = foodCalories.getText();
                    String value4 = foodCarbs.getText();
                    String value5 = foodProtein.getText();
                    String value6 = foodFat.getText();
                    String value7 = foodQuant.getText();
                    String value8 = foodQuantMeasure.getText();
                    
                    //store values in array
                    String toInsert[] = {value1, value2, value3, value4, value5, value6, value7, value8};
                    
                    //call our function to insert the newly entered food
                    int confirmation = Food.insertFood(toInsert);
                    
                    //check if succeeded or failed
                    if (confirmation == 0) 
                    {
                        //fail
                        JOptionPane.showMessageDialog(getParent(), "Something went wrong  ):");
                    } 
                    else if (confirmation == 1) 
                    {
                        //succeed. 
                        JOptionPane.showMessageDialog(getParent(), "Food Added Successfully!");
                        
                        //repopulate JList using existing query rather than
                        //creating new one to get the ID of newly entered food
                        //and creating an object to add
                        fridge = Food.initilizeFoodList(0);                     
                        foodList = Food.initilizeFoodList(5);
                        recipeList = Recipe.initilizeRecipeList();
                            
                        populateRecipe();
                        populateFridge();
                        populateFoodDict();
                    }
                }
            }
        });
        
        
        
        /*
        *
        *   Food Dictionary Tab Buttons
        *
        */
        jButton8.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e){
                //variable declaration
                int confirmation = 0; 
                int index = jList4.getSelectedIndex();
                DefaultListModel model = (DefaultListModel) jList4.getModel();
                
                Food item = (Food) jList4.getSelectedValue();
                
                //check if a value is selected
                if (jList4.getSelectedIndex() == -1) 
                {
                    JOptionPane.showMessageDialog(getParent(), "Please select an item!");
                } 
                else 
                {
                    //display a confirmation of Yes | NO | Cancel
                    //0=yes 1=no 2=cancel
                    int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this item?");
                    if (input == 0) 
                    {
                        //call our delete function/query
                        //use confirmation to check if succeeded
                        confirmation = Food.deleteFood(item.getFoodID());

                        //check if succeeded or failed
                        if (confirmation == 0) 
                        {
                            //fail
                            JOptionPane.showMessageDialog(getParent(), "Something went wrong  ):");
                        } 
                        else if (confirmation == 1) 
                        {
                            //succeed. remove item from JList as well
                            JOptionPane.showMessageDialog(getParent(), "Food Deleted Successfully!");
                            model.removeElementAt(index);

                            //repopulate JList using existing query rather than
                            //creating new one to get the ID of newly entered food
                            //and creating an object to add
                            fridge = Food.initilizeFoodList(0);
                            foodList = Food.initilizeFoodList(5);
                            recipeList = Recipe.initilizeRecipeList();

                            populateRecipe();
                            populateFridge();
                            populateFoodDict();
                        }
                    }
                    
                    
                }
                
            }
        });
        
        //the + button (add/insert)
        jButton7.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e){
                //variable declaration
                JTextField foodName = new JTextField();
                JTextField foodGroup = new JTextField();
                JTextField foodCalories = new JTextField();
                JTextField foodCarbs = new JTextField();
                JTextField foodProtein = new JTextField();
                JTextField foodFat = new JTextField();
                JTextField foodQuant = new JTextField();
                JTextField foodQuantMeasure = new JTextField();
                
                Object[] foodInfo ={
                    "Name: ", foodName,
                    "Food Group: ", foodGroup,
                    "Calories: ", foodCalories,
                    "Carbs: ", foodCarbs,
                    "Protein: ", foodProtein,
                    "Fat: ", foodFat,
                    "Quantity: ", foodQuant,
                    "Measurement Type (oz, Fl.oz, etc.): ", foodQuantMeasure
                };

                int option = JOptionPane.showConfirmDialog(getParent(), foodInfo, "Enter Information for new Food", JOptionPane.OK_CANCEL_OPTION);
                if(option == JOptionPane.OK_OPTION)
                {
                    String value1 = foodName.getText();
                    String value2 = foodGroup.getText();
                    String value3 = foodCalories.getText();
                    String value4 = foodCarbs.getText();
                    String value5 = foodProtein.getText();
                    String value6 = foodFat.getText();
                    String value7 = foodQuant.getText();
                    String value8 = foodQuantMeasure.getText();
                    
                    String toInsert[] = {value1, value2, value3, value4, value5, value6, value7, value8};
                    
                    int confirmation = Food.insertFood(toInsert);
                    
                    //check if succeeded or failed
                    if (confirmation == 0) 
                    {
                        //fail
                        JOptionPane.showMessageDialog(getParent(), "Something went wrong  ):");
                    } 
                    else if (confirmation == 1) 
                    {
                        //succeed. 
                        JOptionPane.showMessageDialog(getParent(), "Food Added Successfully!");
                        
                        //repopulate JList using existing query rather than
                        //creating new one to get the ID of newly entered food
                        //and creating an object to add
                        fridge = Food.initilizeFoodList(0);                     
                        foodList = Food.initilizeFoodList(5);
                        recipeList = Recipe.initilizeRecipeList();
                            
                        populateRecipe();
                        populateFridge();
                        populateFoodDict();
                    }
                }
            }
        });
        
        /*
        *
        *   Recipe Listeners
        *
        */
        jList2.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent evt){
                JList list = (JList) evt.getSource();
                if(evt.getClickCount() == 1)
                {
                    //int index = jList2.getSelectedIndex();
                    DefaultListModel model = (DefaultListModel) jList2.getModel();
                    DefaultListModel modelInstruc = (DefaultListModel) jList6.getModel();
                    modelInstruc.removeAllElements();
                
                    Recipe item = (Recipe) jList2.getSelectedValue();
                    
                    modelInstruc.addElement(item.getInstructions());
                    jList6.setModel(modelInstruc);
                    jTextField13.setText(String.valueOf(item.getFats()));
                    jTextField14.setText(String.valueOf(item.getProteins()));
                    jTextField15.setText(String.valueOf(item.getCarbs()));
                    jTextField16.setText(String.valueOf(item.getCalories()));
                    
                    jTextField17.setText(String.valueOf(item.getCalories()/item.getServingsMade()));
                    jTextField18.setText(String.valueOf(item.getCarbs()/item.getServingsMade()));
                    jTextField19.setText(String.valueOf(item.getProteins()/item.getServingsMade()));
                    jTextField20.setText(String.valueOf(item.getFats()/item.getServingsMade()));
                    
                    jTextField21.setText(String.valueOf(item.getServingsMade()));
                }
            }
        });
        
        jButton3.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent e){
                //variable declaration
                int confirmation = 0; 
                int index = jList2.getSelectedIndex();
                DefaultListModel model = (DefaultListModel) jList2.getModel();
                
                Recipe item = (Recipe) jList2.getSelectedValue();
                
                //check if a value is selected
                if (jList2.getSelectedIndex() == -1) 
                {
                    JOptionPane.showMessageDialog(getParent(), "Please select an item!");
                } 
                else 
                {
                    //display a confirmation of Yes | NO | Cancel
                    //0=yes 1=no 2=cancel
                    int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this item?");
                    if (input == 0) 
                    {
                        //call our delete function/query
                        //use confirmation to check if succeeded
                        confirmation = Recipe.deleteRecipe(item.getRecID());

                        //check if succeeded or failed
                        if (confirmation == 0) 
                        {
                            //fail
                            JOptionPane.showMessageDialog(getParent(), "Something went wrong  ):");
                        } 
                        else if (confirmation == 1) 
                        {
                            //succeed. remove item from JList as well
                            JOptionPane.showMessageDialog(getParent(), "Recipe Deleted Successfully!");
                            model.removeElementAt(index);

                            //repopulate JList using existing query rather than
                            //creating new one to get the ID of newly entered food
                            //and creating an object to add
                            fridge = Food.initilizeFoodList(0);
                            foodList = Food.initilizeFoodList(5);
                            recipeList = Recipe.initilizeRecipeList();

                            populateRecipe();
                            populateFridge();
                            populateFoodDict();
                        }
                    }
                    
                    
                }
                
            }
        });
        
        /*
        *
        *   Meal Plan Listeners
        *
        */
        jComboBox1.addActionListener (new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int toPass = 0;
                String day = (String) jComboBox1.getSelectedItem().toString();
                
                if (day == "Sunday") 
                {
                    toPass = 1;
                } 
                else if (day == "Monday") 
                {
                    toPass= 2;
                } 
                else if (day == "Tuesday") 
                {
                    toPass = 3;
                } 
                else if (day == "Wednesday") 
                {
                    toPass = 4;
                } 
                else if (day == "Thursday") 
                {
                    toPass = 5;
                } 
                else if (day == "Friday") 
                {
                    toPass = 6;
                } 
                else if (day == "Saturday") 
                {
                    toPass = 7;
                }
                
                DefaultListModel modelToPass = (DefaultListModel) jList1.getModel();
                sortJListMealPlan(modelToPass, toPass);
            }
        });
    }
    
    /*
    *
    *   General Functions
    *
    */
    void sortJListMealPlan(DefaultListModel model, int day)
    {
        for(MealPlan s : mealList)
        {
            if(!(s.getDayOfWeek() == day))
            {
                if(model.contains(s))
                {
                    model.removeElement(s);
                }
            }
            else
            {
                if(!model.contains(s))
                {
                    model.addElement(s);
                }
            }
        }
    }
    
    /*
    *
    *   Fridge Functions
    *
    */
    void populateFridge() 
    {
        DefaultListModel dlm = new DefaultListModel();
        
        for(Food x : fridge)
        {
            dlm.addElement(x);
        }
        
        jList3.setModel(dlm);
        jList3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList3.setLayoutOrientation(JList.VERTICAL);
        jList3.setVisibleRowCount(-1);
        
        
    }
    
    /*
    *
    *   Food Dictionary Functions
    *
    */
    void populateFoodDict()
    {
        DefaultListModel dlm = new DefaultListModel();
        
        for(Food x : foodList)
        {
            dlm.addElement(x);
        }
        
        jList4.setModel(dlm);
        jList4.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList4.setLayoutOrientation(JList.VERTICAL);
        jList4.setVisibleRowCount(-1);
    }

    void populateRecipe()
    {
        DefaultListModel dlm = new DefaultListModel();
        DefaultListModel dlmInstruc = new DefaultListModel();
        
        for(Recipe x : recipeList)
        {
            dlm.addElement(x);
        }
        
        jList2.setModel(dlm);
        jList2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList2.setLayoutOrientation(JList.VERTICAL);
        jList2.setVisibleRowCount(-1);
        
        jList6.setModel(dlmInstruc);
        jList6.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList6.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        jList6.setVisibleRowCount(3);
    }
    
    void populateMealPlan()
    {
        DefaultListModel dlm = new DefaultListModel();
        
        for(MealPlan x : mealList)
        {
            dlm.addElement(x);
        }
        
        jList1.setModel(dlm);
        jList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList1.setLayoutOrientation(JList.VERTICAL);
        jList1.setVisibleRowCount(-1);
    }

    /**
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jTextField13 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jTextField19 = new javax.swing.JTextField();
        jTextField20 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jList6 = new javax.swing.JList();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jList5 = new javax.swing.JList<>();
        jTextField31 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jTextField32 = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jTextField33 = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList4 = new javax.swing.JList();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("jMealPlanner");
        setSize(d.width, d.height);

        jTabbedPane5.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" }));

        jLabel2.setText("Day:");

        jTextField1.setColumns(5);

        jLabel3.setText("Meal Calories:");

        jLabel4.setText("Meal Fat:");

        jTextField2.setColumns(5);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jTextField3.setColumns(5);

        jLabel5.setText("Meal Protein:");

        jLabel6.setText("Meal Carbs:");

        jTextField4.setColumns(5);

        jButton1.setFont(new java.awt.Font("Ubuntu Light", 0, 48)); // NOI18N
        jButton1.setText("+");

        jButton2.setFont(new java.awt.Font("Ubuntu Light", 0, 48)); // NOI18N
        jButton2.setText("-");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel7.setText("Total Daily Calories:");

        jTextField5.setColumns(5);

        jTextField6.setColumns(5);

        jLabel8.setText("Total Daily Carbs:");

        jLabel9.setText("Total Daily Protein:");

        jLabel10.setText("Total Daily Fat:");

        jTextField7.setColumns(5);

        jTextField8.setColumns(5);

        jLabel11.setText("Total Weekly Calories:");

        jLabel12.setText("Total Weekly Carbs:");

        jLabel13.setText("Totaly Weekly Protein:");

        jLabel14.setText("Total Weekly Fat:");

        jTextField9.setColumns(5);

        jTextField10.setColumns(5);

        jTextField11.setColumns(5);

        jTextField12.setColumns(5);

        jScrollPane2.setViewportView(jList1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel11))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel12)))
                                .addGap(20, 20, 20)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel13))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel14)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(134, 268, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel7)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel12)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel9)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel10)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane5.addTab("Meal Plans", jPanel1);

        jScrollPane1.setViewportView(jList2);

        jTextField13.setColumns(5);

        jLabel17.setText("Total Fat:");

        jTextField14.setColumns(5);

        jTextField15.setColumns(5);

        jTextField16.setColumns(5);

        jLabel18.setText("Total Calories:");

        jLabel19.setText("Total Carbs:");

        jLabel20.setText("Total Protein:");

        jLabel21.setText("Protein per Serving:");

        jLabel22.setText("Fat per Serving:");

        jLabel23.setText("Carbs per Serving:");

        jLabel24.setText("Calories per Serving:");

        jTextField17.setColumns(5);

        jTextField18.setColumns(5);

        jTextField19.setColumns(5);

        jTextField20.setColumns(5);

        jLabel25.setText("Servings:");

        jTextField21.setColumns(5);

        jButton3.setFont(new java.awt.Font("Ubuntu Light", 0, 48)); // NOI18N
        jButton3.setText("-");
        jButton3.setPreferredSize(new java.awt.Dimension(72, 72));

        jButton4.setFont(new java.awt.Font("Ubuntu Light", 0, 48)); // NOI18N
        jButton4.setText("+");

        jScrollPane6.setViewportView(jList6);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel21))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel22)))
                                .addGap(98, 98, 98)
                                .addComponent(jLabel25)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel24)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel23)
                                        .addGap(34, 34, 34)
                                        .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 298, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane6)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                    .addComponent(jScrollPane6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24)
                            .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(jLabel23)
                            .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21)
                            .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(jLabel22)
                            .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25)
                            .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane5.addTab("Recipes", jPanel2);

        jScrollPane3.setViewportView(jList3);

        jButton5.setFont(new java.awt.Font("Ubuntu Light", 0, 48)); // NOI18N
        jButton5.setText("+");

        jButton6.setFont(new java.awt.Font("Ubuntu Light", 0, 48)); // NOI18N
        jButton6.setText("-");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 757, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane5.addTab("Fridge", jPanel3);

        jList5.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(jList5);

        jTextField31.setColumns(5);

        jLabel39.setText("Purchased:");

        jTextField32.setColumns(5);

        jLabel40.setText("Needed:");

        jTextField33.setColumns(5);

        jLabel41.setText("Owned:");

        jButton9.setText("OK");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel39)
                            .addComponent(jLabel40)
                            .addComponent(jLabel41))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton9)))
                        .addGap(0, 850, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39)
                    .addComponent(jButton9))
                .addContainerGap())
        );

        jTabbedPane5.addTab("Shopping List", jPanel4);

        jScrollPane4.setViewportView(jList4);

        jButton7.setFont(new java.awt.Font("Ubuntu Light", 0, 48)); // NOI18N
        jButton7.setText("+");

        jButton8.setFont(new java.awt.Font("Ubuntu Light", 0, 48)); // NOI18N
        jButton8.setText("-");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 757, Short.MAX_VALUE)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTabbedPane5.addTab("Food Dictionary", jPanel5);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane5, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane5)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * 
     * 
     * 
     * 
     * 
     * @param args the command line arguments
     * 
     * 
     * 
     * 
     * 
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrontEnd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrontEnd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrontEnd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrontEnd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrontEnd().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JList jList3;
    private javax.swing.JList jList4;
    private javax.swing.JList<String> jList5;
    private javax.swing.JList jList6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
