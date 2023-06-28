package cn.coding.com.netsolutionapp.service;

import cn.coding.com.netsolutionapp.exception.CustomerNotFoundException;
import cn.coding.com.netsolutionapp.model.Customer;
import cn.coding.com.netsolutionapp.model.paging.Paged;
import cn.coding.com.netsolutionapp.model.paging.Paging;
import cn.coding.com.netsolutionapp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    public Customer getCustomerById(Integer id) throws CustomerNotFoundException {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return customer.get();
        }
        throw new CustomerNotFoundException("Customer not found with ID " + id);
    }

    public void deleteById(Integer id) throws CustomerNotFoundException {
        Long count = customerRepository.countById(id);
        if (count == null || count == 0) {
            throw new CustomerNotFoundException("Customer not found with ID " + id);
        }
        customerRepository.deleteById(id);
    }

    public Paged<Customer> getCustomerWithPaginated(int pageNumber, int size) {
        PageRequest request = PageRequest.of(pageNumber -1, size, Sort.Direction.ASC, "id");
        Page<Customer> customerPage = customerRepository.findAll(request);
        return new Paged<>(customerPage, Paging.of(customerPage.getTotalPages(), pageNumber, size));
    }

    public void getByName(String name) throws CustomerNotFoundException {
        customerRepository.findFirstByName(name);
    }
}
