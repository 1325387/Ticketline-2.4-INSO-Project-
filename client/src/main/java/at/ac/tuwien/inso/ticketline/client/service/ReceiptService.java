package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.dto.TicketIdentifierDto;

import java.util.List;

/**
 * @author Jayson Asenjo 1325387
 *
 * Creates a Receipt after selling process
 */
public interface ReceiptService {

    /**
     * Creates a receipt for given items as PDF.
     *
     * @param items sold items
     * @throws ServiceException
     */
    public void createPDF(List<TicketIdentifierDto> items) throws ServiceException;

}
