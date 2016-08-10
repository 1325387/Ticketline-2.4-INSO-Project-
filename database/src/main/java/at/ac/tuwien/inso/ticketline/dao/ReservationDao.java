package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Reservation;
import at.ac.tuwien.inso.ticketline.model.TicketIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Lina Wang 1326922
 */
@Repository
public interface ReservationDao extends JpaRepository<Reservation,Integer> {

    /**
     * Finds all news ordered by the submission date.
     *
     * @return the list of news
     */
    @Query(value = "SELECT r FROM Reservation r WHERE r.customer.id = :customerId")
    public List<Reservation> findReservationsByCustomer(@Param("customerId")Integer customerId);

}
