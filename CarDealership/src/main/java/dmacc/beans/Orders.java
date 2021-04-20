/**
 * @author Jaden Schuster - jdschuster
 * CIS175 - Spring 2021
 * Apr 19, 2021
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
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String carBrand;
	private String carName;
	private String carYear;
	private String carType;
	private String carColor; 
	private String carPrice;
	private String optionName;
	
	public Orders(long id, String carBrand, String carName, String carYear, String carType, String carColor,
			String carPrice) {
		super();
		this.id = id;
		this.carBrand = carBrand;
		this.carName = carName;
		this.carYear = carYear;
		this.carType = carType;
		this.carColor = carColor;
		this.carPrice = carPrice;
	}

	public Orders(long id, String carBrand, String carName, String carYear, String carType, String carColor,
			String carPrice, String optionName) {
		super();
		this.id = id;
		this.carBrand = carBrand;
		this.carName = carName;
		this.carYear = carYear;
		this.carType = carType;
		this.carColor = carColor;
		this.carPrice = carPrice;
		this.optionName = optionName;
	}
	public void setCar(Cars c) {
		this.carBrand = c.getCarBrand(); 
		this.carName = c.getCarName(); 
		this.carYear = c.getCarYear();  
		this.carType = c.getCarType(); 
		this.carColor = c.getCarColor(); 
		this.carPrice = c.getCarPrice(); 
	}

}
