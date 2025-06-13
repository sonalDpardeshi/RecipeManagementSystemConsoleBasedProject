package org.techhub.recipe.controller;

import java.util.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.techhub.recipe.config.Config;
import org.techhub.recipe.model.RecipeModel;
import org.techhub.recipe.service.*;

public class Controller {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		RecipeModel recipemodel = null;
		List<RecipeModel> list = null;
		boolean back = false;
		
		// Initialize the Spring context and get the RecipeService bean
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
		RecipeService recipeservice = (RecipeService) context.getBean("recipeService");

		do {
			// Displaying the menu options for the user
			System.out.println("1.add new recipe \n" + 
			"2.View all available recipes (only names)\n"+
			"3.delete by id\n" + 
			"4.update \n" + 
			"5.suggest recipes for my ingredients) \n" + 
			"6.exit \n"+
			"choose your choice: ");
			int choice = sc.nextInt();
			
			System.out.println("---------------------------");
			switch (choice) {

			case 1:// Add a new recipe
				try {
					sc.nextLine();
					System.out.println("Enter recipe details(name,ingredients,process,duration(in mins),date('yyyy-mm-dd')): ");
					String name = sc.nextLine();
					String ingredients = sc.nextLine();
					String process = sc.nextLine();
					int duration = Integer.parseInt(sc.nextLine());
					String date = sc.nextLine();
					recipemodel = new RecipeModel();
					recipemodel.setName(name);
					recipemodel.setIngredients(ingredients);
					recipemodel.setProcess(process);
					recipemodel.setDuration(duration);
					recipemodel.setDate(date);

					// Calling the add method from RecipeService to add the recipe
					boolean flag = recipeservice.add(recipemodel);

					if (flag) {
						System.out.println("Recipe added success.....");
					} else {
						System.out.println("Recipe not added.....");
					}
				}
				catch (Exception e) {
					System.out.println("Please enter correct details");
				}
				System.out.println("------------------------------");
				break;

			case 2:// View all available recipes
				try {
				list = recipeservice.view();
				if (list.isEmpty()) {
					System.out.println("No recipes are available");
					break;
				}
				do {
					// Displaying the list of recipes
					System.out.println("Available recipes: ");
					for (int i = 0; i < list.size(); i++) {
						System.out.println((i + 1) + " " + list.get(i).getName());
					}

					while (true) {
						System.out.println("---------------------------");
						// Asking user if they want to go back to the recipe list

						System.out.println("Enter id for which you want to see recipe: ");
						choice = sc.nextInt();
						if (choice == 0) {
							break;
						}

						if (choice > 0 && choice <= list.size()) {
							RecipeModel e = list.get(choice - 1);
							System.out.println("--------------------------------");
							System.out.println("Name: " + e.getName());
							System.out.println("Duration: " + e.getDuration() + " minutes");
							System.out.println("Ingredients: " + e.getIngredients());
							String arr[] = e.getProcess().split(",");
							System.out.println("Recipe:");
							for (int i = 0; i < arr.length; i++) {
								System.out.println("\t" + (i + 1) + "." + arr[i]);
							}
						} else {
							System.out.println("Invalid choice...!Please enter correct id");
						}
					}
					System.out.println("Do you want to go back('y'/'Y') or stay here(Any character): ");
					back = sc.next().toLowerCase().charAt(0) == 'y' ? false : true;
				} while (back);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				System.out.println("---------------------------");
				break;

			case 3:// Delete a recipe by ID
				try {
				// Displaying the list of recipes to choose from
				list = recipeservice.view();
				if (list.isEmpty()) {
					System.out.println("No recipes are available");
					break;
				}
				System.out.println("Available recipes: ");
				for (int i = 0; i < list.size(); i++) {
					System.out.println((i + 1) + " " + list.get(i).getName());
				}
				System.out.println("-----------------------------");
				System.out.println("Enter id which you want to delete: ");
				int id = sc.nextInt();
				if (id > 0 && id <= list.size()) {
					// Fetching the recipe ID and calling delete method from service
					int recipeid = list.get(id - 1).getId();
					boolean b = recipeservice.delete(recipeid);
					if (b) {
						System.out.println("deleted sucess.......");
					} else {
						System.out.println(" not deleted.......");
					}
				} else {
					System.out.println("Invalid id...! please enter correct id");
					break;
				}
				}
				catch(Exception e) {
				e.printStackTrace();
				}
				System.out.println("------------------------------");

				break;

			case 4:// Update an existing recipe
				try {
				// Displaying the list of recipes to choose from
				list = recipeservice.view();
				if (list.isEmpty()) {
					System.out.println("No recipes are available");
					break;
				}
				System.out.println("Available recipes: ");
				for (int i = 0; i < list.size(); i++) {
					System.out.println((i + 1) + " " + list.get(i).getName());
				}
				System.out.println("-----------------------------");
				System.out.println("Enter id of recipe which you want to update: ");
				int id = sc.nextInt();
				sc.nextLine();

//					// Taking updated recipe details from the user
				if (id > 0 && id <= list.size()) {
					System.out.println("Enter recipe details(ingredients,process,date('yyyy-mm-dd')): ");
					String ingredients = sc.nextLine();
					String process = sc.nextLine();
					String date = sc.nextLine();

					recipemodel = new RecipeModel();
					recipemodel.setIngredients(ingredients);
					recipemodel.setProcess(process);
					recipemodel.setDate(date);

					int recipeid = list.get(id - 1).getId();
					boolean b = recipeservice.update(recipeid, recipemodel);
					if (b) {
						System.out.println("updated success.......");
					} else {
						System.out.println(" Recipe update failed! Please try again...");
					}
				} else {
					System.out.println("Invalid id...!Please enter correct id");
				}
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				System.out.println("---------------------------");
				break;

			case 5:// Suggest recipes based on available ingredients

				try {
					sc.nextLine();
					// Taking available ingredients from the user
					System.out.println("Enter available at least 2 ingredients(by , separate): ");
					String available = sc.nextLine().toLowerCase();
					String str[] = available.split(",");
					HashSet<String> hs1 = new HashSet<>(Arrays.asList(str));

					// Using a TreeMap to store recipes based on common ingredients
					TreeMap<Integer, List<RecipeModel>> tm = new TreeMap<Integer, List<RecipeModel>>(
							Collections.reverseOrder());

					// Fetching all recipes to compare ingredients
					list = recipeservice.view();
					if (list.size() == 0) {
						System.out.println("No recipes found");
					} else {
						for (RecipeModel recipe : list) {
							HashSet<String> hs2 = new HashSet<String>();
							for (String ingredient : recipe.getIngredients().toLowerCase().trim().split(",")) {
								hs2.add(ingredient.trim());
							}
//		Arrays.asList(recipe.getIngredients().toLowerCase().trim().split(",")));

							HashSet<String> common = new HashSet<>(hs1);
							common.retainAll(hs2);

							// Storing recipes that have common ingredients
							if (!common.isEmpty()) {
								int count = common.size();
								tm.computeIfAbsent(count, k -> new ArrayList<>());
								tm.get(count).add(recipe);
							}
						}
						// Displaying the recipes that can be made with the available ingredients

						int rid = 1;
						List<RecipeModel> recipes = new ArrayList<>();
						List<RecipeModel> recipesList = new ArrayList<>();
						System.out.println("You can prepare below dishes by using available ingredients ");

						for (Map.Entry<Integer, List<RecipeModel>> p1 : tm.entrySet()) {
							if (p1.getKey() > 1) {

//						if (rid < 5) {
								recipes = p1.getValue();
								for (int i = 0; i < recipes.size(); i++) {
									System.out.println(rid + "  " + recipes.get(i).getName() + "\t");
									recipesList.add(recipes.get(i));
									rid++;
								}
							}
						}
						back = true;
						do {
							System.out.println("------------------------");
							System.out.println("Go Back[Y/y]/show recipe[Any character]");
							back = sc.next().toLowerCase().charAt(0) == 'y' ? false : true;
							if (back) {
								System.out.println("---------------------------");

//						// Asking user if they want to see the recipe details
								System.out.println("Enter id for which you want to see recipe: ");
								int id = sc.nextInt();
								id = id - 1;
								RecipeModel id1 = recipesList.get(id);

								if (id >= 0 && id < recipesList.size()) {
									boolean prev = true;
									do {
										if (id >= 0 && id < recipesList.size()) {

											System.out.println("---------------------------");
											System.out.println("Id: " + id);
											System.out.println("Name: " + id1.getName());
											System.out.println("Ingredients: " + id1.getIngredients());
											System.out.println("Recipe:  ");
											String pro[] = id1.getProcess().split(",");
											for (int i = 0; i < pro.length; i++) {
												System.out.println("\t" + (i + 1) + " " + pro[i]);
											}
										}
										System.out.println("--------------------------------");
										System.out.println("Go Back [Y/y]: ");
										prev = sc.next().toLowerCase().charAt(0) == 'y' ? false : true;
									} while (prev);
								} else {
									System.out.println("Invalid id...! please enter a valid id");
									continue;
								}
							} else {
								break;
							}

						} while (back);
					}
					System.out.println("------------------------------");
					break;
				} catch (Exception e) {
					e.printStackTrace();
				}

			case 6:// Exit the program
				System.out.println("--------Thank you--------");
				System.exit(0);
			default:
				System.out.println("Invalid choice...! Please select again.");
				System.out.println("------------------------------");

			}
			
		} while (true);
	}
}
