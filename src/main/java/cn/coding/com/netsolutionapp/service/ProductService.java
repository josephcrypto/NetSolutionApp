package cn.coding.com.netsolutionapp.service;

import cn.coding.com.netsolutionapp.exception.ProductNotFoundException;
import cn.coding.com.netsolutionapp.model.Product;
import cn.coding.com.netsolutionapp.model.paging.Paged;
import cn.coding.com.netsolutionapp.model.paging.Paging;
import cn.coding.com.netsolutionapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> listAll() {
        return  (List<Product>) productRepository.findAll();
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public Product getById(Long id) throws ProductNotFoundException {
        Optional<Product> result = productRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new ProductNotFoundException("Could not find any Product with ID " + id);
    }

    public void deleteById(Long id) throws ProductNotFoundException {
        Long count = productRepository.countById(id);
        if (count == null || count == 0 ) {
            throw new ProductNotFoundException("Could not find any Product with ID " + id);
        }
        productRepository.deleteById(id);
    }

    public Paged<Product> getProductWithPaging(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber -1, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<Product> productPage = productRepository.findAll(request);
        return new Paged<>(productPage, Paging.of(productPage.getTotalPages(), pageNumber, size));

    }
}
