package at.ac.tuwien.inso.ticketline.server.integrationtest.service;

import at.ac.tuwien.inso.ticketline.model.*;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.TicketService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Sissi Zhan 1325880
 */
public class TicketServiceIntegrationTest extends AbstractServiceIntegrationTest {

    @Autowired
    private TicketService service;

    /**
     * Tests the method getTicket()
     */
    @Test
    public void testGetTicket() {
        try {
            Ticket t = this.service.getTicket(1);
            assertTrue(t.getId() == 1);
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    /**
     * Tests the method getAllTickets()
     */
    @Test
    public void testGetAllTickets() {
        try {
            List<Ticket> list = this.service.getAllTickets();
            assertEquals(list.size(), 10);
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    /**
     * Tests the method getAllTicketsOfShow()
     */
    @Test
    public void testGetAllTicketsOfShow() {
        try {
            Integer showId = 2;
            List<Ticket> list = this.service.getAllTicketsOfShow(showId);
            assertEquals(list.size(), 2);
            Ticket t = list.get(0);
            assertTrue(t.getShow().getId() == showId);
            t = list.get(1);
            assertTrue(t.getShow().getId() == showId);
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    /**
     * Tests the method getAllSoldTicketsOfShow()
     */
    @Test
    public void testGetAllSoldTicketsOfShow() {
        try {
            int showId = 2;
            List<Ticket> tickets = this.service.getAllSoldTicketsOfShow(showId);
            assertTrue(tickets.isEmpty());

            showId = 1;
            tickets = this.service.getAllSoldTicketsOfShow(showId);
            assertTrue(tickets.size() == 1);

            tickets = this.service.getAllAvaibleTicketsOfShow(1);
            assertTrue(tickets.size() == 0);

        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    /**
     * Tests the method getAllTicketsOfCustomer()
     */
    @Test
    public void testGetAllTicketsOfCustomer() {
        try {
            Integer customerId = 1;
            List<Ticket> list = this.service.getAllTicketsOfCustomer(customerId);
            assertEquals(list.size(), 2);
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

    /**
     * Tests the methode getAllTicketsOfReservation()
     */
    @Test
    public void testGetAllTicketsOfReservation() {
        try {
            Integer resId = 1;
            List<Ticket> list = this.service.getAllTicketsOfReservation(resId);
            assertTrue(list.size() == 1);
            Ticket t = list.get(0);
            TicketIdentifier ti = t.getTicketIdentifiers().get(0);
            assertTrue(ti.getReservation().getId() == resId);
        } catch (ServiceException e) {
            fail("ServiceException thrown");
        }
    }

}
