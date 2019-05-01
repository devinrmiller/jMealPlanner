# jMealPlanner
Meal planner for Dr. Yang's Spring '19 CSC 545/745 class

In the top left of the window 5 tabs display the different parts of the program
(Recipes)
when the program is initial opened you shown a page with all the recipes in the database. 
	On the left side is the list of recipes, displaying their name and category.
	When a recipe is selected all the information at the bottom of the window is populated with its general nutritional facts.
	Along with the nutritional fact the test area on the right of the window is fill with the instructions that are saved in the database.
	Also, in this window you have the ability to add new recipes or delete existing ones.
		To delete a recipe all that is required is to select the recipe from the list and press the "-" button in the bottom right.
		To add a new a new recipe, press the "+" button
			This will open a new window with all the needed info for a recipe
				Name, category, instructions, and servings are all stared text fields
					Servings must be a number
				To add an ingredient to the recipe you must select the ingredient form the list. enter the amount that is required and then press the add Ingredient button.
				this will add the ingredient to the bottom box so you can see all the ingredients enter. 
			press ok to complete the creation

(Fridge)			
When Fridge is selected in the top left a list of all food where the amount currently available is >0
	The "-" button will delete the selected item entirely from the database
	The "+" button opens a window to enter the information about a food and enter it into the database
	
(Food Dictionary)
Food Dictionary will very similar to Fridge.
	Instead of showing just the food where quantity is >0, FD shows ALL foods in the database
	In addition to the add and delete buttons a quantity update button is given
		to change the quantity of an item select it in the list, enter the amount in the text box then press update
		
(Meal Plan)
Meal is used to display the Meal that are planned for the day. 
	At the top left the day of the week can be selected to look at the meals plans for each day
	when the day is selected then the info in the bottom left is populated with the Nutritional facts for the day
	The "-" button will clear the day of all its meals
	"+" opens a window in which you select a recipe and then select the meal (breakfast, lunch, or dinner). that meal is then set to that recipe
	
(Shopping List)
Shopping List is a very simple View. 
	This screen is used to display how much and what you need for the week that is planned.
	if the quantity of a food on hand is not enough to complete each recipe for the week it is display here with its name and how much is needed.
	
INSTALLATION INSTRUCTIONS

Included with this deployment is the driver necessary to connect to an Oracle Database. Include it as a library for the project by:
- Right Clicking on the project in NetBeans
- Selecting Libraries
- Selecting Add Driver/Library
- Navigate to the destination of the driver and select it
- Select ok

Included with this deployment is a build .jar file. Ensure your java is up to date, and simply navigate to \jMealPlanner\jMealPlanner\dist

Double Click the .jar file to run it.

