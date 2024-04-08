package tacos.repositories;

import tacos.models.TacoOrder;

public interface JdbcTemplateOrderRepository {
	TacoOrder save(TacoOrder order);
}
