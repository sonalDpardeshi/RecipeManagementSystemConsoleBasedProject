package org.techhub.recipe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.techhub.recipe.model.RecipeModel;
import org.techhub.recipe.repository.RecipeRepo;

@Service("recipeService")
public class RecipeServiceImpl  implements RecipeService{
	
	// Autowiring the RecipeRepo to interact with the database
	@Autowired
	RecipeRepo reciperepo; 
	
	// Method to add a new recipe by calling the add method from RecipeRepo
	@Override
	public boolean add(RecipeModel recipemodel) {
		return  reciperepo.add(recipemodel);
	}
	
	// Method to fetch all recipes from the database by calling the view method from RecipeRepo
	@Override
	public List<RecipeModel> view() {
		return reciperepo.view();
	}

	 // Method to delete a recipe by its ID by calling the delete method from RecipeRepo
	@Override
	public boolean delete(int recipeid) {
		return reciperepo.delete(recipeid);
	}

	// Method to update an existing recipe by its ID and RecipeModel object by calling the update method from RecipeRepo
	@Override
	public boolean update(int recipeid,RecipeModel recipemodel) {
		return reciperepo.update(recipeid,recipemodel);
	}

}
