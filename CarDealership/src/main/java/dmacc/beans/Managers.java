/**
 * @author Jaden Schuster - jdschuster
 * CIS175 - Spring 2021
 * Apr 10, 2021
 */
package dmacc.beans;


import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
	
	@OneToMany(mappedBy="manager", fetch = FetchType.EAGER)
	List<Options> options; 
	
	public Managers(long id, String mName, String mLastName) {
		super();
		this.id = id;
		this.mName = mName;
		this.mLastName = mLastName;

	}

}
