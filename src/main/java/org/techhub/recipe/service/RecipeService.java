package org.techhub.recipe.service;

import org.techhub.recipe.model.RecipeModel;
import java.util.*;

public interface RecipeService {
	public boolean add(RecipeModel recipemodel);
	
	public List<RecipeModel> view();
	
	public boolean delete(int recipeid);
	
	public boolean update(int recipeid,RecipeModel recipemodel);

}
