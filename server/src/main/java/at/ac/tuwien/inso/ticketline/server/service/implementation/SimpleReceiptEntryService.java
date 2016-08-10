package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.ReceiptEntryDao;
import at.ac.tuwien.inso.ticketline.model.ReceiptEntry;
import at.ac.tuwien.inso.ticketline.model.TicketIdentifier;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.ReceiptEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Lina Wang 1326922
 */
@Service
public class SimpleReceiptEntryService implements ReceiptEntryService{

    @Autowired
    private ReceiptEntryDao receiptEntryDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public ReceiptEntry save(ReceiptEntry receiptEntry) throws ServiceException {
        try {
            return receiptEntryDao.save(receiptEntry);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
