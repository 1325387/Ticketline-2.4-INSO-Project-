package at.ac.tuwien.inso.ticketline.server.unittest.service;

import at.ac.tuwien.inso.ticketline.dao.CustomerDao;
import at.ac.tuwien.inso.ticketline.dao.EmployeeDao;
import at.ac.tuwien.inso.ticketline.dao.TicketDao;
import at.ac.tuwien.inso.ticketline.model.*;
import at.ac.tuwien.inso.ticketline.model.Employee;
import at.ac.tuwien.inso.ticketline.model.Ticket;
import at.ac.tuwien.inso.ticketline.model.TicketIdentifier;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.TicketIdentifierService;
import at.ac.tuwien.inso.ticketline.server.service.implementation.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;

/**
 * @author Raphael Schotola 1225193
 */
public class TicketServiceTest {
    private SimpleTicketService simpleTicketService = null;
    private SimpleTicketIdentifierService simpleTicketIdentifierService = null;
    private SimpleReceiptService simpleReceiptService = null;
    private SimpleReceiptEntryService simpleReceiptEntryService = null;
    private SimpleReservationService simpleReservationService = null;

    private List<Ticket> allTicketsOfShow = null;
    private List<Ticket> availableTickets = null;
    private List<Ticket> reservedTicketsToSell = null;
    private List<TicketIdentifier> ticketIdentifiersA = null;
    private List<TicketIdentifier> ticketIdentifiersB = null;
    private Ticket availableTicket = null;
    private Ticket reservedTicket = null;
    private Reservation reservation = null;
    private TicketIdentifier ticketIdentifierA = null;
    private TicketIdentifier ticketIdentifierB1 = null;
    private TicketIdentifier ticketIdentifierB2 = null;
    private TicketIdentifier newTicketAValidIdentifier = null;

    /**
     * ---------------------Cancel Ticket Variables---------------
     */

    private List<Ticket> ticketsToCancel = null;
    private List<TicketIdentifier> ticketIdentifiersC = null;
    private Ticket ticketToCancel = null;
    private TicketIdentifier validIdentifier = null;
    private TicketIdentifier invalidIdentifier = null;
    private Employee employee = null;

    @Before
    public void setUp() {
        simpleTicketService = new SimpleTicketService();
        simpleTicketIdentifierService = new SimpleTicketIdentifierService();
        simpleReceiptService = new SimpleReceiptService();
        simpleReceiptEntryService = new SimpleReceiptEntryService();

        allTicketsOfShow = new ArrayList<>();
        availableTickets = new ArrayList<>();

        ticketIdentifiersA = new ArrayList<>();
        ticketIdentifiersB = new ArrayList<>();

        availableTicket = new Ticket();
        reservedTicket = new Ticket();

        reservation = new Reservation(null, null, null);

        ticketIdentifierA = new TicketIdentifier(availableTicket, false, null, null, null, null);
        ticketIdentifierB1 = new TicketIdentifier(reservedTicket, true, null, null, null, reservation);
        ticketIdentifierB2 = new TicketIdentifier(reservedTicket, false, null, null, null, null);

        newTicketAValidIdentifier = new TicketIdentifier(availableTicket, true, "", "", null, null);
        newTicketAValidIdentifier.setId(1);
        availableTicket.setPrice(10);
        availableTicket.setId(1);
        availableTicket.setTicketIdentifiers(ticketIdentifiersA);

        ticketIdentifiersA.add(ticketIdentifierA);
        ticketIdentifiersB.add(ticketIdentifierB1);
        ticketIdentifiersB.add(ticketIdentifierB2);

        availableTicket.setTicketIdentifiers(ticketIdentifiersA);
        reservedTicket.setTicketIdentifiers(ticketIdentifiersB);

        allTicketsOfShow.add(availableTicket);
        allTicketsOfShow.add(reservedTicket);

        availableTickets.add(availableTicket);

        ticketsToCancel = new ArrayList<>();
        ticketIdentifiersC = new ArrayList<>();

        ticketToCancel = new Ticket();
        ticketToCancel.setId(1);

        validIdentifier = new TicketIdentifier(ticketToCancel, true, "", "", null, null);
        validIdentifier.setId(1);
        invalidIdentifier = new TicketIdentifier(ticketToCancel, false, "No Reason", "", null, null);
        invalidIdentifier.setId(1);
        ticketIdentifiersC.add(validIdentifier);
        ticketToCancel.setTicketIdentifiers(ticketIdentifiersC);
        ticketsToCancel.add(ticketToCancel);

        employee = new Employee();
        employee.setId(1);

        reservedTicketsToSell = new ArrayList<>();
        reservedTicketsToSell.add(reservedTicket);

    }

    @Test
    public void testGetAllAvailableTicketsOfShow() throws ServiceException {
        TicketDao ticketDao = Mockito.mock(TicketDao.class);
        Mockito.when(ticketDao.findByShowId(1)).thenReturn(allTicketsOfShow);

        simpleTicketService.setTicketDao(ticketDao);
        List<Ticket> result = simpleTicketService.getAllAvaibleTicketsOfShow(1);
        assertTrue(result.contains(availableTicket));
        assertTrue(!result.contains(reservedTicket));
    }

    @Test
    public void testGetAllReservedTicketsOfShow() {
        TicketDao ticketDao = Mockito.mock(TicketDao.class);
        Mockito.when(ticketDao.findByShowId(1)).thenReturn(allTicketsOfShow);

        simpleTicketService.setTicketDao(ticketDao);

        try {
            List<Ticket> result = simpleTicketService.getAllReservedTicketsOfShow(1);
            assertTrue(result.contains(reservedTicket));
            assertTrue(!result.contains(availableTicket));
        } catch (ServiceException e) {
            fail("Exception thrown");
        }
    }

    @Test
    public void testSellTicket() throws ServiceException {

        CustomerDao customerDao = Mockito.mock(CustomerDao.class);
        EmployeeDao employeeDao = Mockito.mock(EmployeeDao.class);
        TicketDao ticketDao = Mockito.mock(TicketDao.class);

        simpleTicketIdentifierService = Mockito.mock(SimpleTicketIdentifierService.class);
        simpleReceiptService = Mockito.mock(SimpleReceiptService.class);
        simpleReceiptEntryService = Mockito.mock(SimpleReceiptEntryService.class);
        simpleReservationService = Mockito.mock(SimpleReservationService.class);

        simpleTicketService.setTicketIdentifierService(simpleTicketIdentifierService);
        simpleTicketService.setCustomerDao(customerDao);
        simpleTicketService.setEmployeeDao(employeeDao);
        simpleTicketService.setTicketDao(ticketDao);
        simpleTicketService.setReceiptService(simpleReceiptService);
        simpleTicketService.setReceiptEntryService(simpleReceiptEntryService);
        simpleTicketService.setReservationService(simpleReservationService);


        Mockito.when(simpleTicketIdentifierService.save(any(TicketIdentifier.class))).thenReturn(newTicketAValidIdentifier);
        //Mockito.when(simpleTicketIdentifierService.save(any(TicketIdentifier.class))).then(returnsFirstArg());
        Mockito.when(customerDao.findOne(any(Integer.class))).thenReturn(null);
        Mockito.when(employeeDao.findOne(any(Integer.class))).thenReturn(null);
        Mockito.when(simpleReceiptService.save(any(Receipt.class))).thenReturn(null);
        Mockito.when(simpleReceiptEntryService.save(any(ReceiptEntry.class))).thenReturn(null);
        Mockito.when(ticketDao.findOne(any(Integer.class))).thenReturn(availableTicket);

        Mockito.when(simpleReservationService.findReservationsByCustomer(any(Integer.class))).thenReturn(new ArrayList<Reservation>());

        List<TicketIdentifier> result = simpleTicketService.sellTicket(availableTickets, 1, 1);
        assertTrue(result.contains(newTicketAValidIdentifier));
        assertTrue(result.size() == 1);
    }


    @Test
    public void testCancelTicket() throws ServiceException {
        TicketDao ticketDao = Mockito.mock(TicketDao.class);
        EmployeeDao employeeDao = Mockito.mock(EmployeeDao.class);
        TicketIdentifierService ticketIdentifierService = Mockito.mock(TicketIdentifierService.class);

        Mockito.when(ticketDao.findOne(1)).thenReturn(ticketToCancel);
        Mockito.when(employeeDao.findOne(1)).thenReturn(employee);
        Mockito.when(ticketIdentifierService.makeInvalid(any(TicketIdentifier.class), any(Employee.class))).thenReturn(invalidIdentifier);

        simpleTicketService.setTicketDao(ticketDao);
        simpleTicketService.setEmployeeDao(employeeDao);
        simpleTicketService.setTicketIdentifierService(ticketIdentifierService);

        List<TicketIdentifier> result = simpleTicketService.cancelTicket(ticketsToCancel, "No Reason");
        assertTrue(ticketsToCancel.size() == 1);
        assertTrue(result.size() == 1);

        for (TicketIdentifier t : result) {
            assertFalse(t.getValid());
            assertEquals(t.getTicket().getId(), ticketsToCancel.get(0).getId());
        }

    }
}
