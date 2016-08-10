package at.ac.tuwien.inso.ticketline.server.rest;

import at.ac.tuwien.inso.ticketline.client.util.BundleManager;
import at.ac.tuwien.inso.ticketline.dto.*;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.StripeService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.aspectj.bridge.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * @author Raphael Schotola 1225193
 */
@Api(value = "stripe", description = "stripe REST service")
@RestController
@RequestMapping(value = "/stripe")
public class StripeController {

    @Autowired
    private StripeService stripeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(StripeController.class);


    /**
     * Makes a Stripe payment
     *
     * @param dto the Stripe dto
     * @return a Message -> success or error
     * @throws ServiceException
     */
    @ApiOperation(value = "does a stripe transaction")
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public MessageDto pay(@ApiParam(name = "stripe", value = "transaction to do") @Valid @RequestBody StripeDto dto) throws ServiceException {
        MessageDto msg = new MessageDto();
        msg.setType(MessageType.SUCCESS);


        String validateMessage = validateStripeDto(dto);
        LOGGER.debug("Validate Message: " + validateMessage);
        if (validateMessage.length() != 0) {
            msg.setText(validateMessage);
            msg.setType(MessageType.ERROR);
            return msg;
        }

        try {
            this.stripeService.pay(dto);
        } catch (ServiceException e) {
            msg.setText(e.getMessage());
            msg.setType(MessageType.ERROR);
        }
        return msg;
    }

    /**
     * @param stripeDto the stripe dto
     * @return message if its valid or not
     * @author Lina Wang, 1326922
     * <p>
     * Validates a stripe dto
     */
    public String validateStripeDto(StripeDto stripeDto) {
        String errorMessage = "";
        int cent = stripeDto.getAmountInCent();
        String cardNnumber = stripeDto.getCardNumber();
        CustomerDto customerDto = stripeDto.getCustomer();
        int month = stripeDto.getExpMonth();
        int year = stripeDto.getExpYear();
        List<TicketDto> tickets = stripeDto.getTickets();


        if (cent < 0) {
            errorMessage += "Negative cent value\n";
        }

        if (cardNnumber != null || cardNnumber.equals("")) {
            if (!cardNnumber.matches("[0-9\\s]*")) {
                errorMessage += BundleManager.getExceptionBundle().getString("invalidcard.error") + "\n";
            }
        } else {
            errorMessage += BundleManager.getExceptionBundle().getString("stripecard.error") + "\n";
        }
        LocalDate localDate = LocalDate.MAX.now();
        int currentYear = localDate.getYear()-2000;
        int currentMonth = localDate.getMonth().getValue();


        LOGGER.debug("Year: " + year);
        LOGGER.debug("Current: " + currentYear);
        LOGGER.debug("Month: " + month);
        LOGGER.debug("Current: " + currentMonth);

        if (currentYear > year) {
            errorMessage += BundleManager.getExceptionBundle().getString("expired.error")+"\n";
        } else if (currentYear == year) {
            if (currentMonth > month || month > 12) {
                errorMessage += BundleManager.getExceptionBundle().getString("expired.error")+"\n";
            }
        }

        if (!tickets.isEmpty()) {
            for (TicketDto t : tickets) {
                errorMessage += validateTicketDto(t);
            }
        } else {
            errorMessage += "No tickets to sell\n";
        }
        return errorMessage;
    }

    /**
     * @param customerDto the customer dto
     * @return a message if its valid or not
     * @author Lina Wang, 1326922
     * <p>
     * Validates a customer dto
     */
    public String validateCustomerDto(CustomerDto customerDto) {
        String errorMessage = "";
        String address = customerDto.getAddress().getStreet();
        LocalDate dob = customerDto.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String firstname = customerDto.getFirstname();
        String lastname = customerDto.getLastname();
        String phone = customerDto.getPhoneNumber();
        String email = customerDto.getEmail();
        String city = customerDto.getAddress().getCity();
        String plz = customerDto.getAddress().getPostalCode();

        if (address == null || address.length() == 0) {
            errorMessage += "Please enter your address!\n";
        } else if (!address.matches("[a-zA-Z0-9\\s\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u00FF]*")) {
            errorMessage += BundleManager.getExceptionBundle().getString("lettersnumbersinput.error");
        }
        if (dob == null) {
            errorMessage += BundleManager.getMessageBundle().getString("dob.warning") + "\n";
        }
        if (phone == null) {
            errorMessage += BundleManager.getMessageBundle().getString("phone.warning") +"\n";
        } else if (!phone.matches("[0-9]+")) {
            errorMessage += BundleManager.getExceptionBundle().getString("onlynumbersallowed.error") + "\n";
        }
        //Check if customer is over 14 and under 110
        LocalDate currentDate = LocalDate.now(); //
        LocalDate ageLimitOver14 = currentDate.minusYears(14);
        LocalDate ageLimitUnder110 = currentDate.minusYears(110);

        if (dob.isAfter(ageLimitOver14)) {
            errorMessage += BundleManager.getMessageBundle().getString("14older.warning") + "\n";
        }
        if (dob.isBefore(ageLimitUnder110)) {
            errorMessage += BundleManager.getMessageBundle().getString("110younger.warning") + "\n";
        }
        if (firstname == null || firstname.length() == 0) {
            errorMessage += BundleManager.getMessageBundle().getString("firstname.warning") + "\n";
        } else if (!firstname.matches("[a-zA-Z\\s\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u00FF]*")) {
            errorMessage += BundleManager.getExceptionBundle().getString("lettersinput.error") + "\n";
        }

        if (lastname == null || lastname.length() == 0) {
            errorMessage += BundleManager.getMessageBundle().getString("lastname.warning") + "\n";
        } else if (!lastname.matches("[a-zA-Z\\s\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u00FF]*")) {

            errorMessage += BundleManager.getExceptionBundle().getString("lettersinput.error") + "\n";
        }

        if (email != null) {
            if (!email.matches("^.+@.+\\..+$")) {
                errorMessage += BundleManager.getMessageBundle().getString("email.warning") + "\n";
            }
        }

        if (plz != null){
            if (!plz.matches("[0-9]+")){
                errorMessage += BundleManager.getMessageBundle().getString("invalidplz.error") + "\n";
            }
        }

        return errorMessage;
    }

    /**
     * @param ticketDto the ticket dto
     * @return message if its valide or not
     * @author Lina Wang, 1326922
     * <p>
     * Validates a ticket dt
     */
    public String validateTicketDto(TicketDto ticketDto) {
        String errorMessage = "";
        double price = ticketDto.getPrice();
        int seat = ticketDto.getSeatId();

        if (price < 1) {
            errorMessage += "Invalid ticket price\n";
        }

        if (seat < 1) {
            errorMessage += "Invalid seat of ticket\n";
        }
        return errorMessage;
    }


}
