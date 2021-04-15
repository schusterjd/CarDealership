package dmacc.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="options")
public class Options {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id; 
	private String optionName;
	@ManyToOne
	@JoinColumn(name="car_id")
	private Cars car; 
	
}
