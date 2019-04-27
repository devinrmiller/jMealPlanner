-- Base database for 745 Group Project
drop table Recipe cascade constraints;
drop table Food cascade constraints;
drop table MealPlan cascade constraints;
drop table Ingredients cascade constraints;

create table Recipe(
	recID char(10) primary key,
	category char(30) not null,
	instructions varchar2(300) not null,
	servingsTotal number not null,
	servingsRemain number not null,
	constraint servingsTotal check (servingsTotal >= 1),										--Must make at least 1 serving
	constaint servingsRemain check (servingsRemain >= 0)										--Cannot have negative servings remaining
);

create table Food(
	foodID char(10) primary key,
	groupType char(30) not null,
	calories number not null,
	carbs number not null,
	protein number not null,
	fat number not null,
	name varchar2(30) not null,
	quantity number not null,													--quantity on hand
	quantityMeasurement char(20) not null,												--oz, grams, lbs, etc.
);																							

create table MealPlan(
	dayOfWeek char(2) primary key,
	breakfast char(10) not null,													--breakfast, lunch and dinner refernce a recipe ID
	lunch char(10) not null,
	dinner char(10) not null,
	constraint fk_breakfast foreign key (breakfast) references Recipe(recID),
	constraint fk_lunch foreign key (lunch) references Recipe(recID),
	constraint fk_dinner foreign key (dinner) references Recipe(recID)
);

create table Ingredients(
	recID char(10),
	foodID char(10),
	quantity number not null,													--quantity on needed
	quantityMeasurement char(20) not null,														
	constraint fk_recipe_recID foreign key (recID) references Recipe(recID),
	constraint fk_food_foodID foreign key (foodID) references Food(foodID)
);

--populate Recipes with base data
insert into Recipe values ('1', 'Pasta-Lunch', 'Place dry pasta in boiling water, seasoned with light salt.\n Blah blah blah\n. blah blah.', 12, 0);
insert into Recipe values ('2', 'Salad', 'Mix desired lettuce with tomatoes, croutons, cucumbers and cheese. Toss in Italian dressing.', 12, 0);
insert into Recipe values ('3', 'Grilled Cheese', 'Place cheddar cheese between two slices of bread. Spread butter on top of top slice and bottom of bottom slice of bread. Brown each side over medium-low heat', 12, 0);

--populate Food with base data
insert into Food values ('1', 'Grain', 5, 5, 0, 10, 'Dry Angel Hair Pasta', 16, 'oz');
insert into Food values ('2', 'Other - Oil', 50, 5, 10, 50, 'Olive Oil', 32, 'Fl. oz');
insert into Food values ('3', 'Seasoning', 25, 0, 0, 20, 'Salt', 250, 'oz');
insert into Food values ('4', 'Seasoning', 10, 0, 0, 5, 'Pepper', 250, 'oz');
insert into Food values ('5', 'Vegetable', 75, 0, 5, 15, 'Tomato', 25, 'oz');

insert into Food values ('6', 'Vegetable', 50, 0, 15, 5, 'Lettuce', 15, 'oz');
insert into Food values ('8', 'Grain', 80, 50, 0, 25, 'Croutons', 40, 'oz');
insert into Food values ('9', 'Vegetable', 50, 0, 0, 5, 'Cucumber', 15, 'oz');
insert into Food values ('10', 'Dairy', 70, 10, 15, 50, 'Cheddar Cheese', 25, 'oz');

insert into Food values ('11', 'Grain', 65, 50, 10, 20, 'White Bread', 16, 'oz');
insert into Food values ('12', 'Dairy', 100, 10, 5, 50, 'Butter', 8, 'oz');

--populate MealPlan with base data
insert into MealPlan values ('0', '1', '2', '3');
insert into MealPlan values ('1', '3', '2', '1');
insert into MealPlan values ('2', '2', '1', '3');
insert into MealPlan values ('3', '3', '1', '2');
insert into MealPlan values ('4', '1', '1', '1');
insert into MealPlan values ('5', '2', '2', '2');
insert into MealPlan values ('6', '3', '3', '3');

--populate Ingredients with base data
--pasta
insert into Ingredients values ('1', '1', 16, 'oz');
insert into Ingredients values ('1', '2', 4, 'Fl. oz');
insert into Ingredients values ('1', '3', 1, 'oz');
insert into Ingredients values ('1', '4', 1, 'oz');
insert into Ingredients values ('1', '5', 15, 'oz');

--salad
insert into Ingredients values ('2', '6', 15, 'oz');
insert into Ingredients values ('2', '5', 10, 'oz');
insert into Ingredients values ('2', '8', 5, 'oz');
insert into Ingredients values ('2', '9', 5, 'oz');
insert into Ingredients values ('2', '10', 5, 'oz');

--grilled cheese
insert into Ingredients values ('3', '11', 1, 'oz (2 slices)');
insert into Ingredients values ('3', '10', 3, 'oz');
insert into Ingredients values ('3', '12', .5, 'oz');



--testing 
--select * from Recipe;
--select * from Food;
--select * from MealPlan;
--select * from Ingredients;

