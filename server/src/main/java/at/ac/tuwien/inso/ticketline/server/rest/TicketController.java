package at.ac.tuwien.inso.ticketline.server.rest;

import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.dto.*;
import at.ac.tuwien.inso.ticketline.model.*;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.TicketService;
import at.ac.tuwien.inso.ticketline.server.util.DtoToEntity;
import at.ac.tuwien.inso.ticketline.server.util.EntityToDto;
import at.ac.tuwien.inso.ticketline.server.util.PDFCreator;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lina Wang 1326922, Raphael Schotola 1225193
 */
@Api(value = "ticket", description = "Ticket REST service")
@RestController
@RequestMapping(value = "/ticket")
public class TicketController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    private TicketService ticketService;

    @ApiOperation(value = "Gets all tickets", response = TicketDto.class, responseContainer = "List")
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<TicketDto> getAll() throws ServiceException {
        LOGGER.info("getAll() called");
        return EntityToDto.convertTickets(ticketService.getAllTickets());
    }

    @ApiOperation(value = "Gets all avaible tickets", response = TicketDto.class, responseContainer = "List")
    @RequestMapping(value = "/show/avaible/{id}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<TicketDto> getAvaibleOfShow(@ApiParam(name = "id", value = "ID of the show") @PathVariable("id") Integer id) throws ServiceException {
        LOGGER.info("getAvaibleOfShow() called");
        if (id < 1) {
            throw new ServiceException("Invalid show ID");
        }
        return EntityToDto.convertTickets(ticketService.getAllAvaibleTicketsOfShow(id));
    }

    /**
     * @param id the show ID
     * @return all sold tickets of the given show
     * @throws ServiceException
     * @author Raphael Schotola 1225193
     * Gets all sold tickets of a specific show
     */
    @ApiOperation(value = "Gets all sold tickets of a show", response = TicketDto.class, responseContainer = "List")
    @RequestMapping(value = "/show/sold/{id}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<TicketDto> getSoldOfShow(@ApiParam(name = "id", value = "ID of the show") @PathVariable("id") Integer id) throws ServiceException {
        LOGGER.info("getSoldOfShow() called");
        if (id < 1) {
            throw new ServiceException("Invalid show ID");
        }
        return EntityToDto.convertTickets(ticketService.getAllSoldTicketsOfShow(id));
    }

    /**
     * @param id the show ID
     * @return all reserved tickets of the given show
     * @throws ServiceException
     * @author Sissi Zhan 1325880
     * <p>
     * Gets all reserved tickets of a specific show
     */
    @ApiOperation(value = "Gets all reserved tickets of a show", response = TicketDto.class, responseContainer = "List")
    @RequestMapping(value = "/show/reserved/{id}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<TicketDto> getReservedOfShow(@ApiParam(name = "id", value = "ID of the show") @PathVariable("id") Integer id) throws ServiceException {
        LOGGER.info("getReservedOfShow() called");
        if (id < 1) {
            throw new ServiceException("Invalid show ID");
        }
        return EntityToDto.convertTickets(ticketService.getAllReservedTicketsOfShow(id));
    }

    /**
     * Gets all tickets of a specific show
     *
     * @param id the show ID
     * @return all tickets of the given show
     * @throws ServiceException
     */
    @ApiOperation(value = "Gets all tickets of a show", response = TicketDto.class, responseContainer = "List")
    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<TicketDto> getAllOfShow(@ApiParam(name = "id", value = "ID of the show") @PathVariable("id") Integer id) throws ServiceException {
        LOGGER.info("getAllOfShow() called");
        if (id < 1) {
            throw new ServiceException("Invalid show ID");
        }
        List<Ticket> tickets = ticketService.getAllTicketsOfShow(id);

        return EntityToDto.convertTickets(tickets);
    }

    /**
     * Gets all tickets of given customer
     *
     * @param customerNumber the number of the customer
     * @return all tickets that the customer bought or reserved
     * @throws ServiceException
     */
    @ApiOperation(value = "Gets all tickets of a customer", response = ViewModelDto.class, responseContainer = "List")
    @RequestMapping(value = "/customer/{customerNumber}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ViewModelDto> getAllOfCustomer(@ApiParam(name = "customerNumber", value = "the customer number") @PathVariable("customerNumber") Integer customerNumber) throws ServiceException {
        LOGGER.info("getAllOfCustomer() called");
        if (customerNumber < 1) {
            throw new ServiceException("Invalid customer number");
        }
        List<ViewModelDto> viewModels = new ArrayList<>();
        List<Ticket> tickets = ticketService.getAllTicketsOfCustomer(customerNumber);

        for (Ticket ticket : tickets) {
            ViewModelDto viewModel = new ViewModelDto();
            viewModel.setTicketId(ticket.getId());
            viewModel.setPrice(ticket.getPrice());
            viewModel.setSeatNumber(ticket.getSeat().getSequence());
            viewModel.setShowName(ticket.getShow().getPerformance().getName());
            viewModels.add(viewModel);
            // find corresponding customer
            for (TicketIdentifier tid : ticket.getTicketIdentifiers()) {
                // DEBUG
                //LOGGER.debug("Iterating over identifiers");
                if (tid.getReservation() != null && tid.getValid()) {
                    LOGGER.debug("Found customer id: " + tid.getReservation().getCustomer().getId());
                    viewModel.setCustomerNumber(tid.getReservation().getCustomer().getId());
                    viewModel.setStatus("reserved");
                    break;
                } else if (tid.getReceiptEntry() != null && tid.getValid()) {
                    viewModel.setCustomerNumber(tid.getReceiptEntry().getReceipt().getCustomer().getId());
                    viewModel.setStatus("sold");
                }
            }
        }
        return viewModels;
    }

    /**
     * Gets all tickets of a specific show
     *
     * @param reservationNumber the number of reservation
     * @return all tickets of given reservation number
     * @throws ServiceException
     */
    @ApiOperation(value = "Gets all tickets of a reservation", response = ViewModelDto.class, responseContainer = "List")
    @RequestMapping(value = "/reservation/{reservationNumber}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<ViewModelDto> getAllOfReservation(@ApiParam(name = "reservationNumber", value = "the number of reservation")
                                                  @PathVariable("reservationNumber") Integer reservationNumber) throws ServiceException {
        LOGGER.info("getAllOfReservation() called");
        if (reservationNumber < 1) {
            throw new ServiceException("Invalid reservation number");
        }
        List<ViewModelDto> viewModels = new ArrayList<>();
        try {
            List<Ticket> tickets = ticketService.getAllTicketsOfReservation(reservationNumber);
            for (Ticket ticket : tickets) {
                ViewModelDto viewModel = new ViewModelDto();
                viewModel.setTicketId(ticket.getId());
                viewModel.setPrice(ticket.getPrice());
                viewModel.setSeatNumber(ticket.getSeat().getSequence());
                viewModel.setShowName(ticket.getShow().getPerformance().getName());
                viewModels.add(viewModel);
                // find corresponding customer
                for (TicketIdentifier tid : ticket.getTicketIdentifiers()) {
                    // DEBUG
                    LOGGER.debug("Iterating over identifiers");
                    LOGGER.debug("Found customer id: " + tid.getReservation().getCustomer().getId());
                    viewModel.setCustomerNumber(tid.getReservation().getCustomer().getId());
                    viewModel.setStatus("reserved");
                    break;
                }
            }

            return viewModels;
        } catch (ServiceException e) {
            throw new ServiceException(e.getMessage());
        }

    }


    /**
     * Sells a ticket
     *
     * @param tickets the tickets to sell
     * @return ticketidentifiers to sold tickets
     * @throws ServiceException
     */
    @ApiOperation(value = "Sells a ticket", response = TicketIdentifierDto.class, responseContainer = "List")
    @RequestMapping(value = "/sell/{employeeId}/{customerId}", method = RequestMethod.POST, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    //@RequestMapping(value = "/sell/{customerId}", method = RequestMethod.POST, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public List<TicketIdentifierDto> sellTicket(@ApiParam(name = "ticket", value = "tickets to sell") @Valid @RequestBody List<TicketDto> tickets,
                                                @ApiParam(name = "customerId", value = "ID of customer") @PathVariable("customerId") Integer customerId,
                                                @ApiParam(name = "employeeId", value = "ID of employee") @PathVariable("employeeId") Integer employeeId) throws ServiceException {
        LOGGER.info("sellTicket() called");
        if (customerId < 1) {
            throw new ServiceException("Invalid customer number");
        }
        /*
        if(employeeId < 1){
            throw new ServiceException("Invalid employee number");
        }
        */
        String validateMessage = "";
        for (TicketDto t : tickets) {
            validateMessage += validateTicketDto(t);
        }
        if (validateMessage.length() != 0) {
            throw new ServiceException("Invalid tickets to sell");
        }
        return EntityToDto.convertTicketIdentifiers(ticketService.sellTicket(DtoToEntity.convertTickets(tickets), customerId, employeeId));
    }

    /**
     * Reserve a ticket
     *
     * @param tickets the tickets to reserve
     * @return ticketidentifiers to reserved tickets
     * @throws ServiceException
     */
    @ApiOperation(value = "Reserves a ticket", response = MessageDto.class)
    @RequestMapping(value = "/reserve/{customerId}", method = RequestMethod.POST, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public MessageDto reserveTicket(@ApiParam(name = "ticket", value = "tickets to reserve") @Valid @RequestBody List<TicketDto> tickets,
                                                   @ApiParam(name = "customerId", value = "ID of customer") @PathVariable("customerId") Integer customerId) throws ServiceException {
        LOGGER.info("reserveTicket() called");
        List<TicketIdentifier> result;
        MessageDto messageDto = new MessageDto();
        if (customerId < 1) {
            throw new ServiceException("Invalid customer number");
        }

        String validateMessage = "";
        for (TicketDto t : tickets) {
            validateMessage += validateTicketDto(t);
        }
        LOGGER.debug("Validate Message: " + validateMessage);
        if (validateMessage.length() != 0) {
            throw new ServiceException("Invalid tickets to reserve");
        }

        try {
            result = ticketService.reserveTicket(DtoToEntity.convertTickets(tickets), customerId);
            messageDto.setType(MessageType.SUCCESS);
            messageDto.setText(result.get(0).getReservation().getId()+"");
            LOGGER.debug("Reserve succeded");
        } catch(ServiceException e) {
            messageDto.setType(MessageType.ERROR);
            messageDto.setText(e.getMessage());
        }
        return messageDto;
    }

    /**
     * Reserve a ticket
     *
     * @param tickets            the tickets to cancel
     * @param cancellationReason the cancellation reason
     * @return ticketidentifiers to cancelled tickets
     * @throws ServiceException
     */
    @ApiOperation(value = "Cancels a ticket", response = MessageDto.class)
    @RequestMapping(value = "/cancel/{cancellationReason}", method = RequestMethod.POST, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public MessageDto cancelTicket(@ApiParam(name = "ticket", value = "tickets to cancel") @Valid @RequestBody List<TicketDto> tickets,
                                   @ApiParam(name = "cancellationReason", value = "cancellation reason") @PathVariable("cancellationReason") String cancellationReason) throws ServiceException {
        LOGGER.info("cancelTicket() called");
        try {

            String validateMessage = "";
            for (TicketDto t : tickets) {
                validateMessage += validateTicketDto(t);
            }
            if (validateMessage.length() != 0) {
                throw new ServiceException("Invalid tickets to cancel");
            }
            ticketService.cancelTicket(DtoToEntity.convertTickets(tickets), cancellationReason);
            MessageDto msg = new MessageDto();
            msg.setType(MessageType.SUCCESS);
            return msg;
        } catch (ServiceException e) {
            MessageDto msg = new MessageDto();
            msg.setType(MessageType.ERROR);
            msg.setText(e.getMessage());
            return msg;
        }

    }

    public String validateTicketDto(TicketDto ticketDto) {
        String errorMessage = "";
        double price = ticketDto.getPrice();
        int seat = ticketDto.getSeatId();

        LOGGER.debug("Price: " + price);
        LOGGER.debug("Seat: " + seat);

        if (price < 0) {
            errorMessage += "Invalid ticket price\n";
        }

        if (seat < 1) {
            errorMessage += "Invalid seat of ticket";
        }
        return errorMessage;
    }

}
