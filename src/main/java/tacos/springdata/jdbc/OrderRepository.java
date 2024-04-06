package tacos.springdata.jdbc;

import org.springframework.data.repository.Repository;

import tacos.models.TacoOrder;

public interface OrderRepository extends Repository<TacoOrder, Long>{
	TacoOrder save(TacoOrder order);
}
