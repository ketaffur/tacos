package tacos.models;

//import org.springframework.data.annotation.Id; // This one is used with Spring Data Jdbc
//import org.springframework.data.relational.core.mapping.Table;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;

@Data
@Entity
//@Table // This one is unnecessary with JPA, must be used with JDBC
@AllArgsConstructor
//@RequiredArgsConstructor // Required arguments are private are final fields
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true) // force is used to initialize final fields to 0
public class Ingredient {
	@Id
	private String id;
	private String name;
	private Type type;
	
	public enum Type {
		WRAP,
		PROTEIN,
		VEGGIES,
		CHEESE,
		SAUCE
	}
}
