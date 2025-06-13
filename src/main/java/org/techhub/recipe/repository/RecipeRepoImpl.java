package org.techhub.recipe.repository;

import java.sql.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.techhub.recipe.model.RecipeModel;

@Repository("recipeRepo")
public class RecipeRepoImpl implements RecipeRepo{
	RecipeModel recipemodel=null;
	int val=0;
	
	// Autowiring the JdbcTemplate for database operations
	@Autowired
	JdbcTemplate template=null;
	
	// Logic to add a new recipe to the database
	@Override
	public boolean add(RecipeModel recipemodel) {
		try {
			PreparedStatementSetter pstmt=new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, recipemodel.getName());
					ps.setString(2, recipemodel.getIngredients());
					ps.setString(3, recipemodel.getProcess());
					ps.setInt(4, recipemodel.getDuration());
					ps.setDate(5,Date.valueOf(recipemodel.getDate()));	
				}
			};
			
			 val=template.update("insert into recipedb values('0',?,?,?,?,?)",pstmt);		
			if(val>0) {return true;}
			else {return false;}
		}
		catch(Exception e) {
			return false;
		}
	}
	
	 // Logic to retrieve all recipes from the database
	RowMapper row=new RowMapper() {
		int count=0;
		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			recipemodel=new RecipeModel();
			
			recipemodel.setId(rs.getInt(1));
			recipemodel.setName(rs.getString(2));
			recipemodel.setIngredients(rs.getString(3));
			recipemodel.setProcess(rs.getString(4));
			recipemodel.setDuration(rs.getInt(5));
			recipemodel.setDate(rs.getString(6));
			return recipemodel;
		}
	};



	@Override
	public List<RecipeModel> view() {
		List<RecipeModel> list=template.query("select * from recipedb",row );
		return list;
	}

	// Method to delete a recipe by its ID
	@Override
	public boolean delete(int recipeid) {
		int value=template.update("delete from recipedb where id=?", new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, recipeid);
			}
		});
		
		if(value>0) {return true;}
		else{return false;}
	}

	// Method to update the details of a recipe by its ID
	@Override
	public boolean update(int recipeid,RecipeModel recipemodel) {

		PreparedStatementSetter pstmt=new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, recipemodel.getIngredients());
				ps.setString(2, recipemodel.getProcess());
				ps.setDate(3, Date.valueOf(recipemodel.getDate()));
				ps.setInt(4, recipeid);
			}
		};
		
		val=template.update("update recipedb set ingredients=?, process=?,date=? where id=? ", pstmt);
		return val>0?true:false;
	}	
}
