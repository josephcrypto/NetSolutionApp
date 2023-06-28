package cn.coding.com.netsolutionapp.repository;

import cn.coding.com.netsolutionapp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findFirstByName(String name);

    Long countById(Integer id);
}
