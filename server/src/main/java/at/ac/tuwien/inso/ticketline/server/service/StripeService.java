package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.dto.StripeDto;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import com.google.common.util.concurrent.Striped;

/**
 * @author Raphael Schotola 1225193
 */
public interface StripeService {
    /**
     * Makes a Stripe payment
     *
     * @param dto the Stripe dto
     * @throws ServiceException
     */
    public void pay(StripeDto dto) throws ServiceException;
}
