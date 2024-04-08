package tacos.jdbctemplate;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.asm.Type;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tacos.models.IngredientRef;
import tacos.models.Taco;
import tacos.models.TacoOrder;
import tacos.repositories.JdbcTemplateOrderRepository;

@Repository
public class JdbcOrderRepository implements JdbcTemplateOrderRepository {
	private JdbcOperations jdbcOperations;
	
	public JdbcOrderRepository(JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	@Override
	@Transactional
	public TacoOrder save(TacoOrder order) {
		PreparedStatementCreatorFactory pscf =
				new PreparedStatementCreatorFactory(
						"insert into Taco_Order "
						+ "(delivery_name, delivery_street, delivery_city, "
						+ "delivery_state, delivery_zip, cc_number, "
						+ "cc_Expiration, cc_cvv, created_at) "
						+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)",
						Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
						Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
						Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP);
		pscf.setReturnGeneratedKeys(true);
		
		order.setCreatedAt(new Date());
		PreparedStatementCreator psc =
				pscf.newPreparedStatementCreator(
						Arrays.asList(
								order.getDeliveryName(),
								order.getDeliveryStreet(),
								order.getDeliveryCity(),
								order.getDeliveryState(),
								order.getDeliveryZip(),
								order.getCcNumber(),
								order.getCcExpiration(),
								order.getCcCVV(),
								order.getCreatedAt()));
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcOperations.update(psc, keyHolder);
		
		long orderId = keyHolder.getKey().longValue();
		order.setId(orderId);
		
		List<Taco> tacos = order.getTacos();
		int i = 0;
		for (Taco taco : tacos) {
			saveTaco(orderId, i++, taco);
		}
		return order;
	}
	
	private long saveTaco(Long orderId, int orderKey, Taco taco) {
		taco.setCreatedAt(new Date());
		
		PreparedStatementCreatorFactory pscf =
				new PreparedStatementCreatorFactory(
						"insert into Taco "
						+ "(name, created_at, taco_order, taco_order_key) "
						+ "values (?, ?, ?, ?)",
						Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG);
		pscf.setReturnGeneratedKeys(true);
		
		PreparedStatementCreator psc =
				pscf.newPreparedStatementCreator(
						Arrays.asList(
								taco.getName(),
								taco.getCreatedAt(),
								orderId,
								orderKey));
		
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcOperations.update(psc, keyHolder);
		
		long tacoId = keyHolder.getKey().longValue();
		
		taco.setId(orderId);
		
//		saveIngredientRefs(tacoId, taco.getIngredients()); // taco entity now stores a list of ingredients, that way it works with spring data jpa
		
		return tacoId;
	}
	
	private void saveIngredientRefs(long tacoId, List<IngredientRef> ingredientRefs) {
		int key = 0;
		for (IngredientRef ingredientRef : ingredientRefs) {
			jdbcOperations.update(
					"insert into Ingredient_Ref (ingredient, taco, taco_key) "
					+ "values (?, ?, ?)",
					ingredientRef.getIngredient(), tacoId, key++);
		}
	}
}
