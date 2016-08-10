package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Lina Wang 1326922
 */
public interface GalleryDao extends JpaRepository<Gallery,Integer> {

    @Query(value = "SELECT g FROM Seat s INNER JOIN s.gallery g WHERE s.id = ?1")
    public Gallery findBySeat(@Param("seatId")Integer seatId);
}
