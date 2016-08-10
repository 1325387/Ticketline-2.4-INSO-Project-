package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.ReceiptDao;
import at.ac.tuwien.inso.ticketline.dao.TicketDao;
import at.ac.tuwien.inso.ticketline.dao.TicketIdentifierDao;
import at.ac.tuwien.inso.ticketline.dto.TicketIdentifierDto;
import at.ac.tuwien.inso.ticketline.model.Receipt;
import at.ac.tuwien.inso.ticketline.model.ReceiptEntry;
import at.ac.tuwien.inso.ticketline.model.Ticket;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.ReceiptService;
import at.ac.tuwien.inso.ticketline.server.util.PDFCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Lina Wang 1326922
 */
@Service
public class SimpleReceiptService implements ReceiptService {

    private static Logger LOGGER = LoggerFactory.getLogger(SimpleReceiptService.class);

    @Autowired
    private ReceiptDao receiptDao;
    @Autowired
    private TicketIdentifierDao ticketIdDao;
    @Autowired
    private TicketDao ticketDao;

    /**
     *  {@inheritDoc}
     */
    @Override
    public Receipt save(Receipt receipt) throws ServiceException {
        try {
            return receiptDao.save(receipt);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createReceipt(List<TicketIdentifierDto> items) throws ServiceException {
        PDFCreator pdf = new PDFCreator();
        LOGGER.debug("PDF Creator initialized.");
        boolean init = true;
        LOGGER.debug("Amount of items: "+items.size());
        for(TicketIdentifierDto dto:items){
            LOGGER.debug("ID: "+dto.getId());
            if(init){
                LOGGER.debug("Calling Receipt.");
                Receipt r = receiptDao.findReceiptByIdentifier(dto.getId());
                if(r==null){
                    throw new ServiceException("ERROR: no Receipt found for TicketIdentifier: "+dto.getId());
                }
                LOGGER.debug("Receipt found: "+r.getId());
                // must be changed to first- and lastname
                LOGGER.debug("Employee username: "+r.getEmployee().getFirstname()+" "+r.getEmployee().getLastname());
                pdf.setEmployee(r.getEmployee().getFirstname()+" "+r.getEmployee().getLastname());
                pdf.setReceiptNumber(r.getId());
                init = false;
            }
            LOGGER.debug(" -- Adding new item");
            ReceiptEntry item = receiptDao.findReceiptEntryByTicketIdentifier(dto.getId());
            if(item == null){
                throw new ServiceException("ERROR: no ReceiptEntry found for TicketIdentifier: "+dto.getId()+".");
            }
            LOGGER.debug("Item = "+item.getId()+"; Amount = "+item.getAmount()+"; Price = "+item.getUnitPrice());
            pdf.addEntry(item);
        }
        // DEBUG
        LOGGER.debug("Creatig and saving pdf.");
        String fileName = "Receipt.pdf";
        pdf.create(fileName);
        return fileName;
    }
}
