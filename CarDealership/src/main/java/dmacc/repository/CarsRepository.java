/**
 * @author Jaden Schuster - jdschuster
 * CIS175 - Spring 2021
 * Apr 10, 2021
 */
package dmacc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dmacc.beans.Cars;

public interface CarsRepository extends JpaRepository<Cars, Long> {
	@Query("SELECT c FROM Cars c where c.available = true")
	List<Cars> findAvailableCars(); 

}
