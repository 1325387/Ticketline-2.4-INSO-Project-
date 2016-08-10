package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.model.Customer;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.List;

/**
 * Service for {@link at.ac.tuwien.inso.ticketline.model.Customer}
 *
 * @author Aysel Oeztuerk 0927011.
 */
public interface CustomerService {
    /**
     * Returns the customer object identified by the given id.
     *
     * @param id of the customer object
     * @return the customer
     * @throws ServiceException the service exception
     */
    public Customer getCustomer(Integer id) throws ServiceException;

    /**
     * Returns all customers.
     *
     * @return java.util.List all customers
     * @throws ServiceException the service exception
     */
    public List<Customer> getAllCustomers() throws ServiceException;

    /**
     * Gets the customers, who resemble the given name.
     *
     * @param name of the customer object
     * @return a list of customers, which resemble the given name
     * @throws ServiceException the service exception
     */
    public List<Customer> getCustomersByName(String name) throws ServiceException;

    /**
     * Creates the given customer new.
     *
     * @param customer customer to create
     * @return created customer
     * @throws ServiceException the service exception
     */
    public Customer createNewCustomer(Customer customer) throws ServiceException;

    /**
     * Updates the given customer.
     *
     * @param customer customer to update
     * @throws ServiceException the service exception
     */
    public void  updateCustomer(Customer customer) throws ServiceException;
}
