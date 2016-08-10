package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.PerformanceType;
import at.ac.tuwien.inso.ticketline.model.Show;
import at.ac.tuwien.inso.ticketline.model.Topten;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO for {@link at.ac.tuwien.inso.ticketline.model.Show}
 *
 * Created by Aysel Oeztuerk 0927011.
 */
@Repository
public interface ShowDao  extends JpaRepository<Show, Integer>, ShowDaoCustom {

    /**
     * Finds all shows.
     *
     * @return the list of shows.
     */
    @Query(value = "SELECT s FROM Show s")
    public List<Show> findAll();

    /**
     * Finds all shows filtered by performance id.
     *
     * @return the list of shows filtered by performance id..
     */
    @Query(value = "SELECT s FROM Show s INNER JOIN " +
            "s.performance p where p.id = ?1") // not performance_id, look @model/Performance
    public List<Show> findByPerformanceId(@Param("id") Integer id);

    /**
     * Finds all shows filtered by ticket id.
     *
     * @return the list of shows filtered by ticket id..
     */
    @Query(value = "SELECT s FROM Show s INNER JOIN " +
            "s.tickets t where t.id = ?1")
    public Show findByTicketId(@Param("id") Integer id);


    /**
     * For testing purposes:
     *
     * Returns the top ten shows of given perfromance type/category by count of sold tickets.
     *
     * Select TOP 10 doesn't work!!! Therefore implemented additional method in ShowDaoImpl.
     */
    @Query(value = "SELECT NEW Topten(sh.id, COUNT(sh.id)) " +
            "FROM Ticket t " +
            "INNER JOIN t.ticketIdentifiers ti " +
            "INNER JOIN t.show sh " +
            "INNER JOIN sh.performance p " +
            "WHERE ti.valid = true AND p.performanceType = ?1 " +
            "GROUP BY sh.id " +
            "ORDER BY COUNT(sh.id) DESC, " +
            "sh.dateOfPerformance DESC ")
    List<Topten> getTop10ShowsOfPerformanceType2(@Param("performanceType") PerformanceType performanceType);

}