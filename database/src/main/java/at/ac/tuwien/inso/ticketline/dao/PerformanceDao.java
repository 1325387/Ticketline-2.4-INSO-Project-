package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Performance;
import at.ac.tuwien.inso.ticketline.model.PerformanceType;
import at.ac.tuwien.inso.ticketline.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

/**
 * DAO for {@link at.ac.tuwien.inso.ticketline.model.Performance}
 *
 * Created by Aysel Oeztuerk 0927011.
 */
@Repository
public interface PerformanceDao extends JpaRepository<Performance, Integer> {

    /**
     * Finds all performances.
     *
     * @return the list of performances
     */
    @Query(value = "SELECT n FROM Performance n")
    public List<Performance> findAll();

    /**
     * @author Sissi Zhan 1325880
     *
     * Finds the performance to a specific show
     *
     * @param showId the ID of the show
     * @return the performance of show
     */
    @Query(value = "SELECT p " +
            "FROM Performance AS p " +
            "LEFT JOIN p.shows AS s " +
            "WHERE s.id = ?1")
    public Performance findOfShow(int showId);

    /**
     * @author Sissi Zhan 1325880
     *
     * Finds performances with given attributes
     * @return a list of performances with given attributes
     */

    @Query(value = "SELECT p " +
            "FROM Performance AS p " +
            "LEFT OUTER JOIN p.participations AS pa " +
            "LEFT OUTER JOIN pa.artist AS a " +
            "INNER JOIN p.shows AS sh " +
            "INNER JOIN sh.tickets AS t " +
            "INNER JOIN sh.room AS r " +
            "INNER JOIN r.location AS l " +
            "WHERE (?1 is null or concat('%', lower(p.name), '%') LIKE concat('%', lower(?1) , '%')) " +
            "AND (?2 is null or l.address.city = ?2) " +
            "AND (?3 is null or l.address.postalCode = ?3) " +
            "AND (?4 is null or p.performanceType = ?4) " +
            "AND (?5 = 0 or t.price <= ?5) " +
            "AND (?6 is null or lower(a.firstname) LIKE concat('%', lower(?6), '%') "+
            "or lower(a.lastname) LIKE concat('%', lower(?6), '%')) " +
            "GROUP BY p.id"
            )
    public List<Performance> filter(@Param("performanceName") String performanceName,
                                    @Param ("town") String town,
                                    @Param ("plz") String plz,
                                    @Param ("performanceType") PerformanceType performanceType,
                                    @Param ("price") int price,
                                    @Param ("artist") String artist);


    /**
     * @author Sissi Zhan 1325880
     *
     * @return all dates of a performance
     */
    @Query(value = "SELECT sh.dateOfPerformance " +
            "FROM Performance AS p " +
            "INNER JOIN p.shows AS sh " +
            "WHERE p.id = ?1")
    public List<Timestamp> getDatesOfPerformance(@Param ("performanceId") int performanceId);

}
