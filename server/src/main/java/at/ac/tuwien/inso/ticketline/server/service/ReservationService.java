package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.model.Reservation;
import at.ac.tuwien.inso.ticketline.model.TicketIdentifier;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.List;

/**
 * @author Lina Wang 1326922
 */
public interface ReservationService {

    /**
     * @author Lina Wang 1326922
     *
     * @param reservation object to persist
     * @return the saved entity
     * @throws ServiceException
     */
    public Reservation save (Reservation reservation) throws ServiceException;

    /**
     * @author Jayson Asenjo 1325387
     *
     * @param customerId customer´s id of resrvations
     * @return all available reservations of this customer
     * @throws ServiceException
     */
    public List<Reservation> findReservationsByCustomer(Integer customerId) throws ServiceException;

}
