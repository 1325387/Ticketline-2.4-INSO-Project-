package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.dto.TicketIdentifierDto;
import at.ac.tuwien.inso.ticketline.model.Receipt;
import at.ac.tuwien.inso.ticketline.model.Ticket;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.List;

/**
 * @author Lina Wang 1326922
 */
public interface ReceiptService {


    /**
     * @author Lina Wang 1326922
     *
     * @param receipt the receipt to create
     * @return the created receipt
     * @throws ServiceException
     */
    public Receipt save (Receipt receipt) throws ServiceException;

    /**
     * @author Jayson Asenjo 1325387
     *
     * @param items items, which are sold
     * @return Receipt name
     * @throws ServiceException
     */
    public String createReceipt(List<TicketIdentifierDto> items) throws ServiceException;

}
