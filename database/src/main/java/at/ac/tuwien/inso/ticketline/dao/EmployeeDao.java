package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO for {@link at.ac.tuwien.inso.ticketline.model.Employee}
 */
@Repository
public interface EmployeeDao extends JpaRepository<Employee, Integer> {

    /**
     * Finds employees by username.
     *
     * @param username the username
     * @return the list of employees
     */
    public List<Employee> findByUsername(String username);

}
