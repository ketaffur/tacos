package tacos.repositories.springdata.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import tacos.models.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long>{
	List<TacoOrder> findByDeliveryZip(String deliveryZip);
	List<TacoOrder> readOrdersByDeliveryZipAndCreatedAtBetween(String deliveryZip, Date startDate, Date endDate);
	List<TacoOrder> getByDeliveryZipAndDeliveryCityAllIgnoringCase(String deliveryZip, String deliveryCity);
	List<TacoOrder> findByDeliveryCityOrderByDeliveryName(String deliveryCity);
	
//	@Query("Order o where o.deliveryCity='Seattle'")
//	List<TacoOrder> readOrdersDeliveredInSeattle();
}
