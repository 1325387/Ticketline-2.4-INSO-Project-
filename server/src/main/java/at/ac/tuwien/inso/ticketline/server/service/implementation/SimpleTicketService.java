package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.dao.*;
import at.ac.tuwien.inso.ticketline.model.*;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.*;
import at.ac.tuwien.inso.ticketline.server.util.PDFCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Mark Jayson Asenjo 1325387
 */
@Service
public class SimpleTicketService implements TicketService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTicketService.class);

    @Autowired
    private TicketDao ticketDao;
    @Autowired
    private TicketIdentifierService ticketIdentifierService;
    @Autowired
    private ReceiptService receiptService;
    @Autowired
    private ReceiptEntryService receiptEntryService;
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private TicketIdentifierDao dao;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Ticket> getAllTickets() throws ServiceException {
        try {
            return ticketDao.findAll();
        } catch (Exception e) {
            throw new ServiceException();
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Ticket> getAllSoldTicketsOfShow(Integer showId) throws ServiceException {
        try {
            return ticketDao.findSoldByShowId(showId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Ticket> getAllAvaibleTicketsOfShow(Integer showId) throws ServiceException {
        List<Ticket> availableTickets = new ArrayList<>();
        Boolean freeTicket;

        try {
            List<Ticket> allTicketsOfShow = this.getAllTicketsOfShow(showId);

            for (Ticket ticket : allTicketsOfShow) {
                freeTicket = true;
                List<TicketIdentifier> tiList = ticket.getTicketIdentifiers();
                if (!tiList.isEmpty()) {
                    for (TicketIdentifier ti : tiList) {
                        if (ti.getValid() == true) {
                            freeTicket = false;
                        }
                    }
                }
                if (freeTicket == true) {
                    availableTickets.add(ticket);
                }
            }
            return availableTickets;
        } catch (Exception e) {
            throw new ServiceException();
        }
    }

    @Override
    public List<Ticket> getAllReservedTicketsOfShow(Integer showId) throws ServiceException {
        List<Ticket> reservedTickets = new ArrayList<>();

        try {
            List<Ticket> allTicketsOfShow = this.getAllTicketsOfShow(showId);

            for (Ticket ticket : allTicketsOfShow) {
                List<TicketIdentifier> tiList = ticket.getTicketIdentifiers();
                if (!tiList.isEmpty()) {
                    for (TicketIdentifier ti : tiList) {
                        if (ti.getValid() == true) {
                            if (ti.getReservation() != null) {
                                if (ti.getReceiptEntry() == null) {
                                    reservedTickets.add(ticket);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return reservedTickets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Ticket getTicket(Integer id) throws ServiceException {
        try {
            return ticketDao.findOne(id);
        } catch (Exception e) {
            throw new ServiceException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Ticket> getAllTicketsOfShow(Integer id) throws ServiceException {
        try {
            return ticketDao.findByShowId(id);
        } catch (Exception e) {
            throw new ServiceException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Ticket> getAllTicketsOfCustomer(Integer customerNumber) throws ServiceException {
        List<Ticket> allTicketsOfCustomer = new ArrayList<>();
        try {
            allTicketsOfCustomer.addAll(ticketDao.findSoldByCustomerName(customerNumber));
            allTicketsOfCustomer.addAll(ticketDao.findReservedByCustomerName(customerNumber));

            return allTicketsOfCustomer;
        } catch (Exception e) {
            throw new ServiceException();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Ticket> getAllTicketsOfReservation(Integer reservationId) throws ServiceException {
        try {
            LOGGER.debug("Reservation number: " + reservationId);
            return ticketDao.findByReservationNumber(reservationId);
        } catch (Exception e) {
            throw new ServiceException("ERROR while retrieving all tickets of a reservation @getAllTicketsOfReseravtion()" + "\n" + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Ticket save(Ticket ticket) throws ServiceException {
        try {
            return ticketDao.save(ticket);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TicketIdentifier createIdentifier(Ticket ticket, Reservation reservation) throws ServiceException {
        try {
            TicketIdentifier ticketIdentifier = new TicketIdentifier(ticket, true, "", "", null, reservation);
            ticketIdentifier = ticketIdentifierService.save(ticketIdentifier);
            return ticketIdentifier;

        } catch (Exception e) {
            LOGGER.error("createIdentifier(): No ticket found!");
            throw new ServiceException();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TicketIdentifier> sellReservedTicket(List<Ticket> tickets, Integer customerId, Integer employeeId) throws ServiceException {
        LOGGER.debug("sellReservedTicket() called");
        List<TicketIdentifier> tempIdentifiers = new ArrayList<>();
        List<TicketIdentifier> resultIdentifiers = new ArrayList<>();

        //Get other tickets of the reservation
        for (TicketIdentifier identifier : tickets.get(0).getTicketIdentifiers()) {
            if (identifier.getReservation() != null) {
                Reservation r = identifier.getReservation();
                tempIdentifiers.addAll(r.getTicketIdentifiers());
            }
        }

        //Create identifiers for each ticket
        for (Ticket ticket : tickets) {
            Ticket t = ticketDao.findOne(ticket.getId());
            resultIdentifiers.add(createIdentifier(t, null));
        }
        //Make other identifiers invalid
        for (TicketIdentifier identifier : tempIdentifiers) {
            if (identifier.getValid()) {
                ticketIdentifierService.makeInvalid(identifier, employeeDao.findOne(employeeId));
            }
        }

        //Receipts not created here

        return resultIdentifiers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TicketIdentifier> sellTicket(List<Ticket> tickets, Integer customerId, Integer employeeId) throws ServiceException {
        List<Ticket> reservedTickets = new ArrayList<>();
        List<Ticket> ticketsToSell = new ArrayList<>();
        List<TicketIdentifier> tempIdentifiers = new ArrayList<>();
        List<TicketIdentifier> resultIdentifiers = new ArrayList<>();
        Iterator<Ticket> it = tickets.iterator();

        while (it.hasNext()) {
            boolean identifierFound = false;
            Ticket t = ticketDao.findOne(it.next().getId());
            if (t.getTicketIdentifiers() != null) {
                List<TicketIdentifier> identifiers = t.getTicketIdentifiers();
                //Check for reserved tickets
                for (int i = 0; i < identifiers.size() && !identifierFound; i++) {
                    if (identifiers.get(i).getValid() && identifiers.get(i).getReservation() != null) {
                        LOGGER.debug("The ticket is reserved");
                        reservedTickets.add(t);
                        identifierFound = true;
                    } else if (identifiers.get(i).getValid() && identifiers.get(i).getReceiptEntry() != null) {
                        //Ticket is already sold
                        LOGGER.debug("The ticket is sold");
                        identifierFound = true;
                        throw new ServiceException(BundleManager.getMessageBundle().getString("alreadysold.warning"));
                    }
                }
                //If there is no ticket with ticketidentifier which is valid and has a reservation/receipt,
                // then its ticketidentifier is invalid
                LOGGER.debug("The ticket has no ticketidentifiers, which is valid");
                if (!identifierFound) ticketsToSell.add(t);

            } else {
                //Ticket has no ticketidentifiers at at all
                LOGGER.debug("Ticket has no ticketidentifiers at all");
                ticketsToSell.add(t);
            }
        }
        //First sell the reserved tickets, if there is any
        if (!reservedTickets.isEmpty()) {
            resultIdentifiers.addAll(sellReservedTicket(reservedTickets, customerId, employeeId));
            //resultIdentifiers.addAll(sellReservedTicket(reservedTickets, customerId, 1));
        }

        //Now sell the other tickets
        //First create identifiers
        for (Ticket t : ticketsToSell) {
            if (t.getTicketIdentifiers() != null) {
                tempIdentifiers.addAll(t.getTicketIdentifiers());
            }
            resultIdentifiers.add(createIdentifier(t, null));
        }
        //Make the old identifiers invalid
        for (TicketIdentifier identifier : tempIdentifiers) {
            if (identifier.getValid()) {
                ticketIdentifierService.makeInvalid(identifier, employeeDao.findOne(employeeId));
                //ticketIdentifierService.makeInvalid(identifier, employeeDao.findOne(1));
            }
        }

        //Create Receipt
        Receipt receipt = new Receipt(new Date(), TransactionState.PAID, customerDao.findOne(customerId), employeeDao.findOne(employeeId));
        receiptService.save(receipt);

        for (TicketIdentifier identifier : resultIdentifiers) {
            ReceiptEntry receiptEntry = new ReceiptEntry(1, 1, identifier.getTicket().getPrice(), receipt, identifier);
            receiptEntryService.save(receiptEntry);
        }

        return resultIdentifiers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TicketIdentifier> reserveTicket(List<Ticket> tickets, Integer customerId) throws ServiceException {

        //First check if reserved tickets are already reserved or sold
        for(Ticket ticket : tickets) {
            Ticket t = ticketDao.findOne(ticket.getId());
            if (t.getTicketIdentifiers() != null) {
                LOGGER.debug("ticket has identifiers");
                for (TicketIdentifier identifier : t.getTicketIdentifiers()) {
                    if (identifier.getValid() && identifier.getReservation() != null) {
                        LOGGER.debug("already reserved");
                        throw new ServiceException(BundleManager.getMessageBundle().getString("alreadyreserved.warning"));
                    } else if (identifier.getValid() && identifier.getReceiptEntry() != null) {
                        LOGGER.debug("already sold");
                        throw new ServiceException(BundleManager.getMessageBundle().getString("alreadysold.warning"));
                    }
                }
            } else {
                LOGGER.debug("Ticket has no identifiers");
            }
        }

        Reservation reservation = new Reservation();
        reservation.setCustomer(customerDao.findOne(customerId));
        reservation.setEmployee(employeeDao.findOne(1));
        reservationService.save(reservation);


        LOGGER.debug("After checking reserved tickets");

        List<TicketIdentifier> ticketIdentifiers = new ArrayList<>();
        for (Ticket t : tickets) {
            try {
                TicketIdentifier identifier = createIdentifier(t, reservation);
                ticketIdentifiers.add(identifier);

            } catch (ServiceException e) {
                throw new ServiceException(BundleManager.getExceptionBundle().getString("reserved.error"));
            }
        }

        return ticketIdentifiers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TicketIdentifier> cancelTicket(List<Ticket> tickets, String cancellationReason) throws ServiceException {
        List<TicketIdentifier> ticketIdentifiers = new ArrayList<>();
        List<TicketIdentifier> updatedTicketIdentifiers = new ArrayList<>();
        boolean identifierFound = false;

        try {
            for (Ticket t : tickets) {
                Ticket ticket = ticketDao.findOne(t.getId());
                ticketIdentifiers.addAll(ticket.getTicketIdentifiers());
            }


            for (TicketIdentifier identifier : ticketIdentifiers) {
                if (identifier.getValid()) {
                    identifier.setCancellationReason(cancellationReason);
                    updatedTicketIdentifiers.add(ticketIdentifierService.makeInvalid(identifier, employeeDao.findOne(1)));
                    identifierFound = true;
                }
            }

            if (!identifierFound) {
                throw new ServiceException(BundleManager.getExceptionBundle().getString("cancel.error"));
            }

        } catch (ServiceException e) {
            throw new ServiceException(e.getMessage());
        }

        return updatedTicketIdentifiers;
    }

    // -------------------- For Testing purposes --------------------

    /**
     * Sets the ticket dao.
     *
     * @param dao the new ticket dao
     */
    public void setTicketDao(TicketDao dao) {
        this.ticketDao = dao;
    }

    public void setTicketIdentifierService(TicketIdentifierService service) {
        this.ticketIdentifierService = service;
    }

    public void setReceiptService(ReceiptService service) {
        this.receiptService = service;
    }

    public void setReceiptEntryService(ReceiptEntryService service) {
        this.receiptEntryService = service;
    }

    public void setCustomerDao(CustomerDao dao) {
        this.customerDao = dao;
    }

    public void setEmployeeDao(EmployeeDao dao) {
        this.employeeDao = dao;
    }

    public void setReservationService(ReservationService service) {
        this.reservationService = service;
    }

    // -------------------- For Testing purposes --------------------

}
