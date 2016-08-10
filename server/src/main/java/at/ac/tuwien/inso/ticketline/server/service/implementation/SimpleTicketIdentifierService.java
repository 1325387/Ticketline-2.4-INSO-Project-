package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.EmployeeDao;
import at.ac.tuwien.inso.ticketline.dao.ReceiptEntryDao;
import at.ac.tuwien.inso.ticketline.dao.TicketIdentifierDao;
import at.ac.tuwien.inso.ticketline.model.Employee;
import at.ac.tuwien.inso.ticketline.model.ReceiptEntry;
import at.ac.tuwien.inso.ticketline.model.TicketIdentifier;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.ReceiptEntryService;
import at.ac.tuwien.inso.ticketline.server.service.TicketIdentifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Lina Wang 1326922
 */
@Service
public class SimpleTicketIdentifierService implements TicketIdentifierService {

    @Autowired
    private TicketIdentifierDao ticketIdentifierDao;
    @Autowired
    private ReceiptEntryDao receiptEntryDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public TicketIdentifier getTicketIdentifierById(Integer id) throws ServiceException {
        try {
            return ticketIdentifierDao.findOne(id);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<TicketIdentifier> getTicketIdentifierByTicketId(Integer ticketId) throws ServiceException {
        try {
            return ticketIdentifierDao.findByTicketId(ticketId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TicketIdentifier> getAllTicketIdentifiers() throws ServiceException {
        try {
            return ticketIdentifierDao.findAll();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public TicketIdentifier save(TicketIdentifier ticketIdentifier) throws ServiceException {
        try {
            return ticketIdentifierDao.save(ticketIdentifier);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TicketIdentifier makeInvalid(TicketIdentifier ticketIdentifier, Employee employee) throws ServiceException {
        try {
            ticketIdentifier.setValid(false);
            ticketIdentifier.setVoidationTime(new Date());
            ticketIdentifier.setVoidedBy(employee.getFirstname());
            return ticketIdentifierDao.save(ticketIdentifier);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

}
