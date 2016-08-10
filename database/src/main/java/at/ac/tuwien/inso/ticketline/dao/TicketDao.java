package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Ticket;
import org.hibernate.service.spi.ServiceException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author Mark Jayson Asenjo 1325387
 */
@Repository
public interface TicketDao extends JpaRepository<Ticket, Integer>{

    /**
     * @author Lina Wang 1326922
     *
     * @param id show id
     * @return all avaible tickets of given show
     */
    public List<Ticket> findAvaibleByShowId(@Param("id")Integer id);

    /**
     * @author Lina Wang 1326922
     *
     * @param customerNumber the number of the customer
     * @return all tickets that the given customer bought
     */
    @Query(value = "SELECT t FROM Ticket t INNER JOIN t.ticketIdentifiers ti INNER JOIN ti.receiptEntry re " +
            "INNER JOIN re.receipt r INNER JOIN r.customer c WHERE c.id = ?1 AND ti.valid = true")
     public List<Ticket> findSoldByCustomerName(@Param("name")Integer customerNumber);

    /**
     * @author Lina Wang 1326922
     *
     * @param customerNumber the number of the customer
     * @return all tickets that the given customer reserved
     */
    @Query(value = "SELECT t FROM Ticket t INNER JOIN t.ticketIdentifiers ti INNER JOIN ti.reservation r " +
            "INNER JOIN r.customer c WHERE c.id = ?1 AND ti.valid = true")
    public List<Ticket> findReservedByCustomerName(@Param("name")Integer customerNumber);

    /**
     * @author Lina Wang 1326922
     *
     * @param reservationId the reservation id
     * @return all tickets of the given reservation number
     */
    @Query(value = "SELECT t FROM Ticket t INNER JOIN t.ticketIdentifiers ti INNER JOIN ti.reservation r " +
            "WHERE r.id = ?1 AND ti.valid = true")
    public List<Ticket> findByReservationNumber(@Param("reservationId")Integer reservationId);

     /**
     * @author Lina Wang 1326922
     *
     * Find all tickets filtered by show ID
     * @param id show id
     * @return all tickets of given show
     */
    @Query(value = "SELECT t FROM Ticket t INNER JOIN t.show s WHERE s.id  = ?1")
    public List<Ticket> findByShowId(@Param("id")Integer id);

    /**
     * @author Raphael Schotola 1225193
     *
     * @param id show id
     * @return all sold tickets of a given show
     */
    @Query(value = "SELECT t " +
            "FROM Ticket AS t " +
            "INNER JOIN t.ticketIdentifiers AS ti " +
            "INNER JOIN ti.receiptEntry AS re " +
            "INNER JOIN t.show AS s " +
            "WHERE ti.valid = true AND s.id = ?1")
    public List<Ticket> findSoldByShowId(@Param("id")Integer id);

}
