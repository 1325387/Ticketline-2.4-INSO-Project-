package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO for {@link at.ac.tuwien.inso.ticketline.model.Customer}
 *
 * @author Lina Wang 1326922
 */
@Repository
public interface CustomerDao extends JpaRepository<Customer,Integer> {

    /**
     * @author Aysel Öztürk 0927011
     *
     * Finds all customers, which's firstname or lastname resemble the given name.
     *
     * @Param name is the given name
     * @return the list of customers, which resemble the given name.
     */
    @Query(value = "SELECT c FROM Customer c WHERE lower(c.firstname) LIKE concat('%', lower(:name) ,'%') " +
            "OR lower(c.lastname) LIKE concat('%', lower(:name) ,'%')")
    public List<Customer> findByName(@Param("name") String name);

}
