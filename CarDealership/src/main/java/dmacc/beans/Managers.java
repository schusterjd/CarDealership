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
public class Managers {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String mName;
	private String mLastName;
	
	
	public Managers(long id, String mName, String mLastName) {
		super();
		this.id = id;
		this.mName = mName;
		this.mLastName = mLastName;

	}

}
