package at.ac.tuwien.inso.ticketline.server.integrationtest.service;

import at.ac.tuwien.inso.ticketline.model.Seat;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.SeatService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;


/**
 * @author Lina Wang, 1326922
 */
public class SeatServiceIntegrationTest extends AbstractServiceIntegrationTest{

    @Autowired
    private SeatService seatService;

    @Test
    public void testGetAllOfShow() throws ServiceException {
        List<Seat> seats = this.seatService.getAllOfShow(1);
        assertNotNull(seats);
        assertEquals(seats.size(),2);
        assertTrue(seats.get(0).getId()==1);
    }

    @Test
    public void testGetAllOfGallery() throws ServiceException {
        List<Seat> seats = this.seatService.getAllOfGallery(1);
        assertNotNull(seats);
        assertEquals(seats.size(),3);
        assertTrue(seats.get(0).getId()==1);
        assertTrue(seats.get(1).getId()==2);
        assertTrue(seats.get(2).getId()==3);
        assertTrue(seats.get(0).getGallery().getId()==1);
    }
}
