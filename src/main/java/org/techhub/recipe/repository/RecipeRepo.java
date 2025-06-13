package org.techhub.recipe.repository;

import org.techhub.recipe.model.RecipeModel;
import java.util.*;

public interface RecipeRepo {
	
	public boolean add(RecipeModel recipemodel);
	
	public List<RecipeModel> view();
	
	public boolean delete(int recipeid);
	
	public boolean update(int recipeid,RecipeModel recipemodel);
}
