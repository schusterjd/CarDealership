package dmacc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import dmacc.beans.Customers;

public interface CustomersRepository extends JpaRepository<Customers, Long> {

}