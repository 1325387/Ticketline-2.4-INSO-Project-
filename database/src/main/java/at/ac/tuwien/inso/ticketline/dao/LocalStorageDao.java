package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.LocalStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Lina Wang, 1326922
 */
@Repository
public interface LocalStorageDao extends JpaRepository<LocalStorage, Integer> {

    @Query(value = "SELECT l FROM LocalStorage l WHERE l.employeeId = ?1")
    public List<LocalStorage> findByEmployeeId(@Param("id") Integer id);


}
