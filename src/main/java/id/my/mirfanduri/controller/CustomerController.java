package id.my.mirfanduri.controller;

import id.my.mirfanduri.entity.Customer;
import id.my.mirfanduri.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/api/v1/customers"})
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> findAllCustomers() {

        List<Customer> customers = customerService.findAllCustomers();

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
}
