/**
 * @author Jaden Schuster - jdschuster
 * CIS175 - Spring 2021
 * Apr 19, 2021
 */
package dmacc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dmacc.beans.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

}