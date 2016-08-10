package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.model.Reservation;
import at.ac.tuwien.inso.ticketline.model.Ticket;
import at.ac.tuwien.inso.ticketline.model.TicketIdentifier;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import javax.xml.ws.Service;
import java.util.List;

/**
 * @author Mark Jayson Asenjo 1325387
 */
public interface TicketService {

    /**
     * @author Mark Jayson Asenjo 1325387
     *
     * Returns the ticket object identified by the given id.
     *
     * @param id of the ticket object
     * @return the ticket object
     * @throws ServiceException the service exception
     */
    public Ticket getTicket(Integer id) throws ServiceException;

    /**
     * @author Lina Wang 1326922
     *
     * Returns all tickets
     *
     * @return all tickets
     * @throws ServiceException
     */

    public List<Ticket> getAllTickets() throws ServiceException;

    /**
     * @author Lina Wang 1326922, Sissi Zhan 1325880
     *
     * @return all avaible tickets of show
     * @throws ServiceException
     */
    public List<Ticket> getAllAvaibleTicketsOfShow(Integer showId) throws ServiceException;

    /**
     * @author Sissi Zhan 1325880
     *
     * @param showId the ID of the show, which reserved tickets should be returned
     * @return list of reserved tickets of show
     * @throws ServiceException
     */
    public List<Ticket> getAllReservedTicketsOfShow(Integer showId) throws ServiceException;

    /**
     * @author Raphael Schotola 1225193
     *
     * @param showId the id of the show to find tickets for
     * @return all sold tickets for a given show
     * @throws ServiceException
     */
    public List<Ticket> getAllSoldTicketsOfShow(Integer showId) throws ServiceException;

    /**
     * @author Lina Wang 1326922
     *
     * Returns a list of tickets by the given Show ID
     *
     * @param id the show ID
     * @return all tickets of the given show
     * @throws ServiceException
     */
    public List<Ticket> getAllTicketsOfShow(Integer id) throws ServiceException;

    /**
     * @author Lina Wang 1326922
     *
     * Returns a list of tickets of the given customer
     *
     * @param customerNumber the number of the customer
     * @return all tickets of the given customer
     * @throws ServiceException
     */
    public List<Ticket> getAllTicketsOfCustomer(Integer customerNumber) throws ServiceException;

    /**
     * @author Lina Wang 1326922
     *
     * Returns a list of tickets of given reservtion ID
     *
     * @param reservationId the reservation Id
     * @return all tickets of given reservation number
     * @throws ServiceException
     */
    public List<Ticket> getAllTicketsOfReservation(Integer reservationId) throws ServiceException;

    /**
     * @author Mark Jayson Asenjo 1325387
     *
     * Saves the given ticket object and returns the saved entity.
     *
     * @param ticket object to persist
     * @return the saved entity
     * @throws ServiceException the service exception
     */
    public Ticket save(Ticket ticket) throws ServiceException;

    /**
     * @author Mark Jayson Asenjo 1325387
     *
     * Creates a new instance of ticketidentifier
     *
     * @param ticket ticket
     * @param reservation null if its not a reservation
     * @return instance of ticketidentifier
     */
    public TicketIdentifier createIdentifier(Ticket ticket, Reservation reservation) throws ServiceException;

    /**
     * @author Lina Wang, 1326922
     *
     * Sells a reserved ticket and releases unsold tickets
     *
     * @param tickets the tickets to sell
     * @param customerId customer id
     * @return a list of ticketidentifiers
     * @throws ServiceException
     */
    public List<TicketIdentifier> sellReservedTicket(List<Ticket> tickets, Integer customerId, Integer employeeId) throws ServiceException;

    /**
     * @author Lina Wang 13269222
     *
     * Sells a ticket
     *
     * @param tickets tickets to sell
     * @param customerId the ID of customer
     * @return list of ticketIdentifiers
     * @throws ServiceException
     */
    public List<TicketIdentifier> sellTicket(List<Ticket> tickets, Integer customerId, Integer employeeId) throws ServiceException;

    /**
     * @author Lina Wang 13269222
     *
     * Reserves a ticket
     *
     * @param tickets tickets to reserve
     * @param customerId the ID of customer
     * @return list of ticketIdentifiers
     * @throws ServiceException
     */
    public List<TicketIdentifier> reserveTicket(List<Ticket> tickets, Integer customerId) throws ServiceException;

    /**
     * @author Lina Wang 1326922
     *
     * @param tickets the tickets to cancel
     * @param cancellationReason the cancellation reason, can be empty
     * @return list of ticketIdentifiers
     * @throws ServiceException
     */
    public List<TicketIdentifier> cancelTicket(List<Ticket> tickets, String cancellationReason) throws ServiceException;
}
