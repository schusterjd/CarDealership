/**
 * @author Jaden Schuster - jdschuster
 * CIS175 - Spring 2021
 * Apr 10, 2021
 */
package dmacc.beans;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Cars {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String carName;
	private String carYear;
	private String carType;
	
	public Cars(long id, String carName, String carYear, String carType) {
		super();
		this.id = id;
		this.carName = carName;
		this.carYear = carYear;
		this.carType = carType;
	}	

}
