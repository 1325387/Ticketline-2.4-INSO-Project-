package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Lina Wang 1326922
 */
public interface CategoryDao extends JpaRepository<Category,Integer> {


}
