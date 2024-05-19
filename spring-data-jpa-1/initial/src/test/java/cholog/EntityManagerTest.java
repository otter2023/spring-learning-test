package cholog;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EntityManagerTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 비영속 -> 영속
     */
    @Test
    void persist() {
        Customer customer = new Customer("Jack", "Bauer");
        entityManager.persist(customer);

        // assertThat : 두 값을 비교하는 것뿐만 아니라, 값이 특정 조건이나 패턴을 만족하는지 확인하는 데 사용됨
        assertThat(entityManager.find(Customer.class, 1L)).isNotNull();
    }

    /**
     * 영속 -> DB
     */
    @Test
    void flush() {
        String sqlForSelectCustomer = "select * from customer where id = 1";

        Customer customer = new Customer("Jack", "Bauer");
        entityManager.persist(customer);
        customer.updateFirstName("Danial");

        Customer savedCustomer = jdbcTemplate.query(sqlForSelectCustomer, rs -> {
            rs.next();
            return new Customer(
                    rs.getLong("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"));
        });
        assertThat(savedCustomer.getFirstName()).isEqualTo("Jack");

        entityManager.flush();

        Customer updatedCustomer = jdbcTemplate.query(sqlForSelectCustomer, rs -> {
            rs.next();
            return new Customer(
                    rs.getLong("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"));
        });

        assertThat(updatedCustomer.getFirstName()).isEqualTo("Danial");
    }
}
