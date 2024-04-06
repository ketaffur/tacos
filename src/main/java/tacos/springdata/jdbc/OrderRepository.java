package tacos.springdata.jdbc;

import org.springframework.data.repository.CrudRepository;

import tacos.models.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long>{

}
