package at.ac.tuwien.inso.ticketline.server.integrationtest.service;

import at.ac.tuwien.inso.ticketline.model.Reservation;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.ReservationService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import static org.junit.Assert.*;


/**
 * @author Lina Wang, 1326922
 */
public class ReservationServiceIntegrationTest extends AbstractServiceIntegrationTest{

    @Autowired
    private ReservationService reservationService;

    @Test
    public void testFindByCustomerId() throws ServiceException {
        List<Reservation> reservations = this.reservationService.findReservationsByCustomer(1);
        assertNotNull(reservations);
        assertTrue(reservations.size()==1);
        assertTrue(reservations.get(0).getId()==1);
    }
}
