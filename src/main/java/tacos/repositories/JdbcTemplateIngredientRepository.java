package tacos.repositories;

import java.util.Optional;

import tacos.models.Ingredient;

public interface JdbcTemplateIngredientRepository {
	Iterable<Ingredient> findAll();
	Optional<Ingredient> findById(String id);
	Ingredient save(Ingredient ingredient);
}
