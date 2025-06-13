# Recipe Management System (Console Based Project)

This is a console-based Spring Framework application designed to manage and suggest recipes based on user input. Users can add, update, view, delete, and get suggestions for recipes based on available ingredients.

# Tech Stack
* Java 8
* Spring Core / Spring Beans
* Spring JDBC
* Maven
* MySQL 
* Eclipse IDE
* Git

# Features
1. Add New Recipe:   Add recipe details such as name, ingredients, process, duration, and date.

2. View Recipes:   Lists all recipes with a detailed view on selection.

3. Delete Recipe:   Allows deletion of a recipe by its ID.

4. Update Recipe:   Update ingredients, process, and date for a selected recipe.

5. Suggest Recipes: Suggest recipes based on the available ingredients entered by the user. 
   Uses a similarity matching algorithm to compare input ingredients with stored recipes and suggest the best matches.
```
# Project Structure
org.techhub.recipe
│
├── controller          // Contains main logic and user interaction
├── service             // RecipeService interface and its implementation
├── model               // POJO class: RecipeModel
├── config              // Spring Java-based configuration
```
```
# Sample Input

Enter recipe details(name,ingredients,process,duration,date):
> Maggi
> noodles,water,salt
> boil water,add noodles,cook for 2 minutes
> 5
> 2025-06-12
```
```
# Author
Sonal Pardeshi
Java Full Stack Developer | Passionate about coding and building real-time apps
```
