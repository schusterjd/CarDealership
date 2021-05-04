/**
 * @author Jaden Schuster - jdschuster
 * CIS175 - Spring 2021
 * Apr 19, 2021
 */
package dmacc.beans;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.transaction.annotation.Transactional;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Transactional
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@OneToOne(orphanRemoval = true, cascade=CascadeType.ALL)
	@JoinColumn(name="car_id")
	private Cars car; 
	
	@OneToOne(orphanRemoval = true, cascade=CascadeType.ALL)
	@JoinColumn(name="customer_id")
	private Customers customer; 
	
	public Orders(Cars car, Customers customer) { 
		this.car = car; 
		this.customer = customer;
	}
}
