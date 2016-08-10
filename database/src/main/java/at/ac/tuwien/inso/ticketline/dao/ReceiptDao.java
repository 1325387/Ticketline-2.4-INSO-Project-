package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Receipt;
import at.ac.tuwien.inso.ticketline.model.ReceiptEntry;
import at.ac.tuwien.inso.ticketline.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Mark Jayson Asenjo 1325387
 */
@Repository
public interface ReceiptDao extends JpaRepository<Receipt, Integer>{

    @Query("SELECT r FROM Receipt r INNER JOIN  r.receiptEntries re INNER JOIN re.ticketIdentifier ti WHERE ti.id = :id AND ti.valid = true")
    public Receipt findReceiptByIdentifier(@Param("id")Integer identifierId);

    @Query("SELECT re FROM ReceiptEntry re INNER JOIN re.ticketIdentifier ti WHERE ti.id = :id AND ti.valid = true")
    public ReceiptEntry findReceiptEntryByTicketIdentifier(@Param("id")Integer ticketId);

}
