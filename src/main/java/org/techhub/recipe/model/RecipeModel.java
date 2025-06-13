package org.techhub.recipe.model;

import lombok.Data;

@Data
public class RecipeModel {
	private int id;
	private String name;
	private String ingredients;
	private String process;
	private int duration;
	private String date;
	
	public RecipeModel(){}

	public RecipeModel(int id, String name,String ingredients, String process, int duration, String date) {
		super();
		this.id = id;
		this.name = name;
		this.ingredients= ingredients;
		this.process = process;
		this.duration = duration;
		this.date = date;
	}
}
