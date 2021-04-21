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
public class Customers {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String cName;
	private String cLastName;
	private String cPhone;

	
	public Customers(long id, String cName, String cLastName, String cPhone) {
		super();
		this.id = id;
		this.cName = cName;
		this.cLastName = cLastName;
		this.cPhone = cPhone;
	}

}