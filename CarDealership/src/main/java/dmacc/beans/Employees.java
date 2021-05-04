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
public class Employees {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String eName;
	private String eLastName;

	
	public Employees(long id, String eName, String eLastName) {
		super();
		this.id = id;
		this.eName = eName;
		this.eLastName = eLastName;
	
	}

}
