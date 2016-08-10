package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.dto.StripeDto;

/**
 * @author Raphael Schotola 1225193
 */
public interface StripeService {
    /**
     * Makes a Stripe payment
     *
     * @param dto the StripeDto
     * @throws ServiceException
     */
    public void pay(StripeDto dto) throws ServiceException;
}
