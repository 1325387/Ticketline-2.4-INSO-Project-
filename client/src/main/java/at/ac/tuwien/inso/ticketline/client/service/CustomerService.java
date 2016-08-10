package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.dto.CustomerDto;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author Aysel Oeztuerk 0927011
 *
 * The Interface CustomerService.
 */
public interface CustomerService {

    /**
     * Gets the customers.
     *
     * @return the customers
     * @throws ServiceException the service exception
     */
    public List<CustomerDto> getAllCustomers() throws ServiceException;

    /**
     * Gets the customers, who resemble the given name.
     *
     * @param name the given name
     * @return a list of the customers, who resemble the given name
     * @throws ServiceException the service exception
     */
    public List<CustomerDto> getCustomerByName(String name) throws ServiceException;

    /**
      * Creats a new customer.
      *
      * @param customer is the customer to be created new
      * @return the new created customer
      * @throws ServiceException the service exception
    */
    public CustomerDto createNewCustomer(CustomerDto customer) throws ServiceException;

    /**
     * Updates an existent customer.
     *
     * @param customer is the customer to be updated
     * @return the updated customer
     * @throws ServiceException the service exception
     */
    public void updateCustomer(CustomerDto customer) throws ServiceException;
}
