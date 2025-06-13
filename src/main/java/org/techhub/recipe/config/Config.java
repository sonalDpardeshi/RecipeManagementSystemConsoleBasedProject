package org.techhub.recipe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan(basePackages= {"org.techhub.recipe"})
public class Config {

	@Bean(name="datasource")
	public DriverManagerDataSource getDataSource() {
		DriverManagerDataSource datasource=new DriverManagerDataSource();
		datasource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		datasource.setUrl("jdbc:mysql://localhost:3306/recipedb");
		datasource.setUsername("root");
		datasource.setPassword("Sonal@123");
		return datasource;
	}
	
	@Bean(name="template")
	public JdbcTemplate getTemplate() {
		return new JdbcTemplate(this.getDataSource());
	}
}
