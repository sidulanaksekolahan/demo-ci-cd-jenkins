package id.my.mirfanduri.repository;

import id.my.mirfanduri.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
