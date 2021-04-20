/**
 * @author Jaden Schuster - jdschuster
 * CIS175 - Spring 2021
 * Apr 19, 2021
 */
package dmacc.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dmacc.beans.Cars;
import dmacc.beans.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
	@Query("SELECT o FROM Orders o WHERE o.car = ?1")
	Orders findOrderByCarID(Cars c); 
}