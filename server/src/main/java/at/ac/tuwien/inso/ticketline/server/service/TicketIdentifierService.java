package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.model.Employee;
import at.ac.tuwien.inso.ticketline.model.TicketIdentifier;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.Date;
import java.util.List;

/**
 * @author Lina Wang 1326922
 */
public interface TicketIdentifierService {

    /**
     * Returns the ticketidentifier object identified by the given id.
     *
     * @param id of the ticketidentifier object
     * @return the ticketidentifier object
     * @throws ServiceException the service exception
     */
    public TicketIdentifier getTicketIdentifierById(Integer id) throws ServiceException;

    /**
     * @author Lina Wang 1326922
     *
     * Returns a list with ticketidentifiers of given ticket ID
     *
     * @param ticketId the ID of ticket
     * @return list with ticketidentifiers of given ticket
     * @throws ServiceException
     */
    public List<TicketIdentifier> getTicketIdentifierByTicketId(Integer ticketId) throws ServiceException;

    /**
     * @author Lina Wang 1326922
     *
     * Returns a list with all ticketidentifiers
     *
     * @return a list with all ticketidentifiers
     * @throws ServiceException
     */
    public List<TicketIdentifier> getAllTicketIdentifiers() throws ServiceException;

    /**
     * @author Lina Wang 1326922
     *
     * Saves the given ticketidentifier object and returns the saved entity.
     *
     * @param ticketIdentifier object to persist
     * @return the saved entity
     * @throws ServiceException the service exception
     */
    public TicketIdentifier save(TicketIdentifier ticketIdentifier) throws ServiceException;

    /**
     * @author Lina Wang 1326922
     *
     * Sets valid attribute of ticketidentifier and returns the updated entity
     *
     * @param ticketIdentifier the ticketidentifier
     * @param employee the employee
     * @return the updated entity
     * @throws ServiceException
     */
    public TicketIdentifier makeInvalid(TicketIdentifier ticketIdentifier, Employee employee) throws ServiceException;

}
