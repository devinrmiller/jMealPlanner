-- Base database for 745 Group Project
drop table Recipe cascade constraints;
drop table Food cascade constraints;
drop table MealPlan cascade constraints;
drop table Ingredients cascade constraints;
--create sequence recipe_seq start with 1;
--create sequence food_seq start with 1;
--create sequence meal_seq start with 1;

create table Recipe(
	recID number(10) GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
	name char(50) not null,
	category char(30) not null,
	instructions varchar2(300) not null,
	servingsTotal number not null,
	servingsRemain number not null								
);

create table Food(
	foodID number(10) GENERATED ALWAYS as IDENTITY(START with 1 INCREMENT by 1),
	groupType char(30) not null,
	calories number not null,
	carbs number not null,
	protein number not null,
	fat number not null,
	name varchar2(30) not null,
	quantity number not null,																	--quantity on hand
	quantityMeasurement char(20) not null														--oz, grams, lbs, etc.
);																							

create table MealPlan(
	dayOfWeek number(10),
	breakfast number(10),																--breakfast, lunch and dinner refernce a recipe ID
	lunch number(10),
	dinner number(10)
);

create table Ingredients(
	recID number(10),
	foodID number(10),
	quantity number not null,																	--quantity on needed
	quantityMeasurement char(20) not null														
);

--build contraints for tables
alter table Recipe add (constraint rec_pk primary key (recID));
alter table Food add (constraint food_pk primary key (foodID));
alter table MealPlan add (constraint meal_pk primary key (dayOfWeek));

alter table Recipe add (constraint servingsTotal check (servingsTotal >= 1));
alter table Recipe add (constraint servingsRemain check (servingsRemain >= 0));

alter table Food add (constraint quantity check (quantity >= 0));

alter table MealPlan add (constraint fk_breakfast foreign key (breakfast) references Recipe(recID) on delete cascade);
alter table MealPlan add (constraint fk_lunch foreign key (lunch) references Recipe(recID) on delete cascade);
alter table MealPlan add (constraint fk_dinner foreign key (dinner) references Recipe(recID) on delete cascade);

alter table Ingredients add (constraint fk_recipe_recID foreign key (recID) references Recipe(recID) on delete cascade);
alter table Ingredients add (constraint fk_food_foodID foreign key (foodID) references Food(foodID) on delete cascade);

--populate Recipes with base data
insert into Recipe (name, category, instructions, servingsTotal, servingsRemain) values ('Simple Paste', 'Pasta-Lunch', 'Place dry pasta in boiling water, seasoned with light salt.\n Blah blah blah\n. blah blah.', 12, 0);
insert into Recipe (name, category, instructions, servingsTotal, servingsRemain) values ('Tomato Salad', 'Salad', 'Mix desired lettuce with tomatoes, croutons, cucumbers and cheese. Toss in Italian dressing.', 12, 0);
insert into Recipe (name, category, instructions, servingsTotal, servingsRemain) values ('Grilled Cheese', 'Sandwich', 'Place cheddar cheese between two slices of bread. Spread butter on top of top slice and bottom of bottom slice of bread. Brown each side over medium-low heat', 12, 0);

--populate Food with base data
insert into Food (groupType, calories, carbs, protein, fat, name, quantity, quantityMeasurement) values ('Grain', 5, 5, 0, 10, 'Dry Angel Hair Pasta', 16, 'oz');
insert into Food (groupType, calories, carbs, protein, fat, name, quantity, quantityMeasurement) values ('Other - Oil', 50, 5, 10, 50, 'Olive Oil', 32, 'Fl. oz');
insert into Food (groupType, calories, carbs, protein, fat, name, quantity, quantityMeasurement) values ('Seasoning', 25, 0, 0, 20, 'Salt', 250, 'oz');
insert into Food (groupType, calories, carbs, protein, fat, name, quantity, quantityMeasurement) values ('Seasoning', 10, 0, 0, 5, 'Pepper', 250, 'oz');
insert into Food (groupType, calories, carbs, protein, fat, name, quantity, quantityMeasurement) values ('Vegetable', 75, 0, 5, 15, 'Tomato', 25, 'oz');

insert into Food (groupType, calories, carbs, protein, fat, name, quantity, quantityMeasurement) values ('Vegetable', 50, 0, 15, 5, 'Lettuce', 15, 'oz');
insert into Food (groupType, calories, carbs, protein, fat, name, quantity, quantityMeasurement) values ('Grain', 80, 50, 0, 25, 'Croutons', 40, 'oz');
insert into Food (groupType, calories, carbs, protein, fat, name, quantity, quantityMeasurement) values ('Vegetable', 50, 0, 0, 5, 'Cucumber', 15, 'oz');
insert into Food (groupType, calories, carbs, protein, fat, name, quantity, quantityMeasurement) values ('Dairy', 70, 10, 15, 50, 'Cheddar Cheese', 25, 'oz');

insert into Food (groupType, calories, carbs, protein, fat, name, quantity, quantityMeasurement) values ('Grain', 65, 50, 10, 20, 'White Bread', 16, 'oz');
insert into Food (groupType, calories, carbs, protein, fat, name, quantity, quantityMeasurement) values ('Dairy', 100, 10, 5, 50, 'Butter', 8, 'oz');

insert into Food (groupType, calories, carbs, protein, fat, name, quantity, quantityMeasurement) values ('Dairy', 5, 5, 5, 0, 'Cayenne Pepper', 0, 'oz');

--populate MealPlan with base data
insert into MealPlan (dayOfWeek, breakfast, lunch, dinner) values (1, 1, 2, 3);
insert into MealPlan (dayOfWeek, breakfast, lunch, dinner) values (2, 3, 2, 1);
insert into MealPlan (dayOfWeek, breakfast, lunch, dinner) values (3, 2, 1, 3);
insert into MealPlan (dayOfWeek, breakfast, lunch, dinner) values (4, 3, 1, 2);
insert into MealPlan (dayOfWeek, breakfast, lunch, dinner) values (5, 1, 1, 1);
insert into MealPlan (dayOfWeek, breakfast, lunch, dinner) values (6, 2, 2, 2);
insert into MealPlan (dayOfWeek, breakfast, lunch, dinner) values (7, 3, 3, 3);

--populate Ingredients with base data
--pasta
insert into Ingredients (recID, foodID, quantity, quantityMeasurement) values (1, 1, 16, 'oz');
insert into Ingredients (recID, foodID, quantity, quantityMeasurement) values (1, 2, 4, 'Fl. oz');
insert into Ingredients (recID, foodID, quantity, quantityMeasurement) values (1, 3, 1, 'oz');
insert into Ingredients (recID, foodID, quantity, quantityMeasurement) values (1, 4, 1, 'oz');
insert into Ingredients (recID, foodID, quantity, quantityMeasurement) values (1, 5, 15, 'oz');

--salad
insert into Ingredients (recID, foodID, quantity, quantityMeasurement) values (2, 6, 15, 'oz');
insert into Ingredients (recID, foodID, quantity, quantityMeasurement) values (2, 5, 10, 'oz');
insert into Ingredients (recID, foodID, quantity, quantityMeasurement) values (2, 8, 5, 'oz');
insert into Ingredients (recID, foodID, quantity, quantityMeasurement) values (2, 9, 5, 'oz');
insert into Ingredients (recID, foodID, quantity, quantityMeasurement) values (2, 10, 5, 'oz');

--grilled cheese
insert into Ingredients (recID, foodID, quantity, quantityMeasurement) values (3, 11, 1, 'oz (2 slices)');
insert into Ingredients (recID, foodID, quantity, quantityMeasurement) values (3, 10, 3, 'oz');
insert into Ingredients (recID, foodID, quantity, quantityMeasurement) values (3, 12, 1, 'oz');

set linesize 1000;

commit;

--testing 
--select * from Recipe;
--select * from Food;
--select * from MealPlan;
--select * from Ingredients;

