package tacos.helpers;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import tacos.models.Ingredient;
import tacos.repositories.JdbcTemplateIngredientRepository;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {
	private JdbcTemplateIngredientRepository ingredientRepository;
	
	public IngredientByIdConverter(JdbcTemplateIngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}

	@Override
	public Ingredient convert(String id) {
		return ingredientRepository.findById(id).orElse(null);
	}
}
