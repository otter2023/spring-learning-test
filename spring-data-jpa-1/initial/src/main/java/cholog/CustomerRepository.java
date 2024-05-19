package cholog;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findByLastName(String s);

    List<Customer> findByLastNameIgnoreCase(String bauer);

    List<Customer> findByLastNameOrderByFirstNameDesc(String bauer);
}
