package cholog;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QueryingDAO {
    private JdbcTemplate jdbcTemplate;

    public QueryingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
    private final RowMapper<Customer> actorRowMapper = (resultSet, rowNum) -> {
        Customer customer = new Customer(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name")
        );
        return customer;
    };
    추후 rowMapper에 대해 학습해보고 이용해보기
    */

    /**
     * public <T> T queryForObject(String sql, Class<T> requiredType)
     */

    public int count() {
        //TODO : customers 디비에 포함되어있는 row가 몇개인지 확인하는 기능 구현

        // SQL 쿼리: customers 테이블의 모든 행을 카운트.
        String sql = "SELECT COUNT(*) FROM customers";
        // 결과를 Integer 형으로 받는다.
        return jdbcTemplate.queryForObject(sql, Integer.class);

    }

    /**
     * public <T> T queryForObject(String sql, Class<T> requiredType, @Nullable Object... args)
     */
    public String getLastName(Long id) {
        //TODO : 주어진 Id에 해당하는 customers의 lastName을 반환

        String sql = "SELECT last_name FROM customers WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }

    /**
     * public <T> T queryForObject(String sql, RowMapper<T> rowMapper, @Nullable Object... args)
     */
    public Customer findCustomerById(Long id) {
        String sql = "SELECT id, first_name, last_name FROM customers WHERE id = ?";
        //TODO : 주어진 Id에 해당하는 customer를 객체로 반환

        return jdbcTemplate.queryForObject(sql, (resultSet, rowNum) -> {
            return new Customer(
                    resultSet.getLong("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name")
            );
        }, id);
    }

    /**
     * public <T> List<T> query(String sql, RowMapper<T> rowMapper)
     */
    public List<Customer> findAllCustomers() {
        String sql = "SELECT id, first_name, last_name FROM customers";
        //TODO : 저장된 모든 Customers를 list형태로 반환

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            return new Customer(
                    rs.getLong("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name")
            );
        });

    }

    /**
     * public <T> List<T> query(String sql, RowMapper<T> rowMapper, @Nullable Object... args)
     */
    public List<Customer> findCustomerByFirstName(String firstName) {
        String sql = "SELECT id, first_name, last_name FROM customers WHERE first_name = ?";
        //TODO : firstName을 기준으로 customer를 list형태로 반환

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            // 해당 코드 대신 클래스에서 final로 정의된 actorRowMapper를 사용할 수 있다.
            return new Customer(
                    rs.getLong("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name")
            );
        }, firstName);
    }
}
