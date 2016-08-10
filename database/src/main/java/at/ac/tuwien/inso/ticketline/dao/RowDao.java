package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Row;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Lina Wang 1326922.
 */
public interface RowDao extends JpaRepository<Row,Integer> {

}
