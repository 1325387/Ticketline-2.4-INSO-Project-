package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.TicketDao;
import at.ac.tuwien.inso.ticketline.dto.StripeDto;
import at.ac.tuwien.inso.ticketline.dto.TicketDto;
import at.ac.tuwien.inso.ticketline.model.Ticket;
import at.ac.tuwien.inso.ticketline.model.TicketIdentifier;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.StripeService;
import at.ac.tuwien.inso.ticketline.server.util.DtoToEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;

/**
 * Created by Raphael on 13.06.2016.
 */
@Service
public class SimpleStripeService implements StripeService {
    @Autowired
    TicketDao ticketDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public void pay(StripeDto dto) throws ServiceException {
        //check if tickets are valid -> can be sold
        List<TicketDto> ticketDtos = dto.getTickets();
        if (ticketDtos != null) {
            List<Ticket> notSellableTickets = new ArrayList<>();
            for (TicketDto t : ticketDtos) {
                Ticket ticket = ticketDao.findOne(t.getId());

                List<TicketIdentifier> identifiers = ticket.getTicketIdentifiers();

                boolean foundOneThatsOk = false;
                if (identifiers.size() == 0) {
                    foundOneThatsOk = true;
                }
                for (TicketIdentifier ti : identifiers) {
                    if (ti.getValid() == true && ti.getReceiptEntry() == null) {
                        foundOneThatsOk = true;
                    }
                }
                if (!foundOneThatsOk) {
                    notSellableTickets.add(DtoToEntity.convert(t));
                }

            }
            if (notSellableTickets.size() > 0) {
                String message = "";
                for (Ticket t : notSellableTickets) {
                    message += t.getId() + ", ";
                }
                message = message.substring(0, message.length() - 2);
                throw new ServiceException("The following tickets are not available to be sold: " + message);
            }
        }

        //make the payment
        RequestOptions requestOptions = (new RequestOptions.RequestOptionsBuilder()).setApiKey
                ("sk_test_PS2QS6bmvvevrtsBVusVTlFy").build();
        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", dto.getAmountInCent() * 100);
        chargeMap.put("currency", "eur");
        if (!(dto.getCustomer() == null) && dto.getCustomer().getId()!=1) {
            chargeMap.put("description", "Customer ID: " + dto.getCustomer().getId());
        } else {
            chargeMap.put("description", "anonymous Customer");
        }
        Map<String, Object> cardMap = new HashMap<String, Object>();
        cardMap.put("number", dto.getCardNumber());
        cardMap.put("exp_month", dto.getExpMonth());
        cardMap.put("exp_year", dto.getExpYear());
        chargeMap.put("card", cardMap);
        try {
            Charge charge = Charge.create(chargeMap, requestOptions);
            System.out.println(charge);
        } catch (StripeException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
