package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.dto.*;

import java.security.Provider;
import java.util.List;

/**
 * @author Sissi Zhan 1325880
 *
 * The Interface TicketService
 */
public interface TicketService {

    /**
     * @author Lina Wang 1326922
     *
     * @return all tickets
     * @throws ServiceException
     */
    public List<TicketDto> getAll() throws ServiceException;

    /**
     * @author Lina Wang 1326922
     *
     * @param showId the ID of the show
     * @return all ticket of the given show
     * @throws ServiceException
     */
    public List<TicketDto> getAllOfShow(Integer showId) throws ServiceException;

    /**
     * @author Lina Wang 1326922
     *
     * @return all avaible tickets of a show
     * @throws ServiceException
     */
    public List<TicketDto> getAvaibleOfShow(Integer showId) throws ServiceException;

    /**
     * @author Raphael Schotola 1225193
     *
     * @param showId show to find sold tickets for
     * @return all sold tickets of a show
     * @throws ServiceException
     */
    public List<TicketDto> getSoldOfShow(Integer showId) throws ServiceException;

    /**
     * @author Sissi Zhan 1325880
     *
     * @param showId the show to find reserved tickets for
     * @return all reserved tickets of show
     * @throws ServiceException
     */
    public List<TicketDto> getReservedOfShow(Integer showId) throws ServiceException;

    /**
     * @author Lina Wang 132692
     *
     * @param customerNumber the customer number
     * @return a list of tickets that the customer bought or reserved
     * @throws ServiceException
     */
    public List<ViewModelDto> getAllOfCustomer(Integer customerNumber) throws ServiceException;

    /**
     * @author Lina Wang 1326922
     *
     * @param reservationNumber the reservation number
     * @return a list of tickets with given reservation number
     * @throws ServiceException
     */
    public List<ViewModelDto> getAllOfReservation(Integer reservationNumber) throws ServiceException;

    /**
     * @author Lina Wang 1326922
     *
     * @param tickets  the tickets to sell
     * @param customer the customer who buys tickets (null if anonymous)
     * @return list of TicketIdentifiers
     * @throws ServiceException
     */
    public List<TicketIdentifierDto> sellTicket(List<TicketDto> tickets, CustomerDto customer, EmployeeDto employee) throws ServiceException;

    /**
     * @author Lina Wang 1326922
     *
     * @param tickets the tickets to reserve
     * @param customer the customer who reserves tickets (null if anonymous)
     * @return messagedto
     * @throws ServiceException
     */
    public MessageDto reserveTicket(List<TicketDto> tickets, CustomerDto customer) throws ServiceException;

    /* @author Lina Wang 1326922
     *
     * @param tickets the tickets to cancel
     * @param cancellationReason the reason why the customer wants to cancel
     * @throws ServiceException
     */
    public void cancelTicket(List<TicketDto> tickets, String cancellationReason) throws ServiceException;


}
