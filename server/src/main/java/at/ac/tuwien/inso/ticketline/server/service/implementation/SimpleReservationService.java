package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.ReservationDao;
import at.ac.tuwien.inso.ticketline.dao.TicketIdentifierDao;
import at.ac.tuwien.inso.ticketline.model.Reservation;
import at.ac.tuwien.inso.ticketline.model.TicketIdentifier;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Lina Wang 1326922
 */
@Service
public class SimpleReservationService implements ReservationService {

    @Autowired
    private ReservationDao reservationDao;
    @Autowired
    private TicketIdentifierDao ticketIdentifierDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public Reservation save(Reservation reservation) throws ServiceException {
        try {
            return reservationDao.save(reservation);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Reservation> findReservationsByCustomer(Integer customerId) throws ServiceException{
        try {
            return reservationDao.findReservationsByCustomer(customerId);
        } catch(Exception e){
            throw new ServiceException("ERROR when retrieving all reservations from given customerId @findReservationsByCustomer"+"\n"+e.getMessage());
        }
    }

}
