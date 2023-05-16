package cn.coding.com.netsolutionapp.repository;

import cn.coding.com.netsolutionapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    public Long countById(Long id);
}
