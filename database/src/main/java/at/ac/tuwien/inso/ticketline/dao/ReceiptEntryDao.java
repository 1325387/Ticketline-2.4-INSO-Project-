package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.ReceiptEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mark Jayson Asenjo 1325387
 */
@Repository
public interface ReceiptEntryDao extends JpaRepository<ReceiptEntry, Integer>{
}
