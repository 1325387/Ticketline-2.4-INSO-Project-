package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Ticket;
import at.ac.tuwien.inso.ticketline.model.TicketIdentifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Mark Jayson Asenjo 1325387
 */

@Repository
public interface TicketIdentifierDao extends JpaRepository<TicketIdentifier,Integer> {

    /**
     * @author Lina Wang 1326922
     *
     * @param ticketId the ID of ticket
     * @return a list with ticketidentifiers of given ticket
     */
    @Query(value = "SELECT ti FROM TicketIdentifier ti INNER JOIN ti.ticket t WHERE t.id = ?1")
    public List<TicketIdentifier> findByTicketId(@Param("ticketId")Integer ticketId);

    /**
     * @author Lina Wang 1326922
     *
     * @param id the ID of ticketIdentifier
     * @param valid true for valid false for invalid
     * @return a list with ticketidentifiers with updated information
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE TicketIdentifier ti SET ti.valid = ?1, ti.voidationTime = ?2, " +
            "ti.voidedBy = ?3, ti.cancellationReason = ?4 WHERE ti.id = ?5 ")
    public void makeInvalid(@Param("valid")Boolean valid, @Param("voidationTime")Date voidationTime,
                            @Param("voidedBy")String voidedBy, @Param("cancellationReason") String cancellationReason, @Param("id")Integer id);

}
