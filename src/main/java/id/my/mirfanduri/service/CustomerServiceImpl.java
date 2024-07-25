package id.my.mirfanduri.service;

import id.my.mirfanduri.entity.Customer;
import id.my.mirfanduri.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }
}
