package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.model.ReceiptEntry;
import at.ac.tuwien.inso.ticketline.model.TicketIdentifier;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

/**
 * @author Lina Wang 1326922
 */
public interface ReceiptEntryService {

    /**
     * @author Lina Wang 1326922
     *
     * @param receiptEntry the entry of receipt
     * @return the receiptentry entity
     * @throws ServiceException
     */
    public ReceiptEntry save(ReceiptEntry receiptEntry) throws ServiceException;

}
