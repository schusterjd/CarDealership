/**
 * @author Jaden Schuster - jdschuster
 * CIS175 - Spring 2021
 * Apr 10, 2021
 */
package dmacc.beans;



import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Cars {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String carBrand;
	private String carName;
	private String carYear;
	private String carType;
	private String carColor; 
	private String carPrice;
	private boolean available;
	private String imageLoc; 
	@OneToMany(mappedBy="car", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	List<Options> options; 
	
	public Cars(long id, String carName, String carYear, String carType, String carColor, String carPrice) {
		super();
		this.id = id;
		this.carName = carName;
		this.carYear = carYear;
		this.carType = carType;
		this.carColor = carColor;
		this.carPrice = carPrice;
	}
	public String getImageLoc() { 
		if (imageLoc == null) { 
			return "https://i.imgur.com/6SNL0IR.jpg"; 
		}
		return imageLoc; 
	}
}
