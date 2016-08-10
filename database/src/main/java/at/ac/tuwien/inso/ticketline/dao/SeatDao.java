package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Seat;
import at.ac.tuwien.inso.ticketline.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Lina Wang 1326922
 */
@Repository
public interface SeatDao extends JpaRepository<Seat, Integer> {

    /**
     * Finds all seats.
     *
     * @return the list of seats.
     */
    @Query(value = "SELECT s FROM Seat s")
    public List<Seat> findAll();

    /**
     * @author Sissi Zhan 1325880
     *
     * Finds all seats of a show
     * @param id the show
     * @return list of all seats from given show
     */
    @Query(value = "SELECT s " +
            "FROM Seat AS s " +
            "INNER JOIN s.room AS r " +
            "INNER JOIN r.shows AS sh " +
            "WHERE sh.id = ?1 ")
    public List<Seat> findAllOf(@Param("showId")Integer id);

    /**
     * @author Lina Wang 1326922
     *
     * Finds all seats in a gallery
     *
     * @param galleryId id of the gallery
     * @return list of all seats in given gallery
     */
    @Query(value = "SELECT s FROM Seat s INNER JOIN s.gallery g WHERE g.id = ?1")
    public List<Seat> findAllByGallery(@Param("galleryId")Integer galleryId);

}

