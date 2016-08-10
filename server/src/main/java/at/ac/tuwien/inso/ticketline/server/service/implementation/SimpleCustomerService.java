package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.CustomerDao;
import at.ac.tuwien.inso.ticketline.model.Customer;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link CustomerService} interface
 *
 * @author Aysel Oeztuerk 0927011.
 */
@Service
public class SimpleCustomerService implements CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleCustomerService.class);

    @Autowired
    private CustomerDao customerDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer getCustomer(Integer id) throws ServiceException {
        try {
            return customerDao.findOne(id);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Customer> getAllCustomers() throws ServiceException {
        try {
            return customerDao.findAll();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Customer> getCustomersByName(String name) throws ServiceException {
        try {
            if (name.contains(" ")){
                List<Customer> customerList = new ArrayList<>();
                List<Customer> temp = new ArrayList<>();

                LOGGER.debug("Name: " + name);
                String[] parts = name.split(" ");
                for (String part : parts) {
                    LOGGER.debug("for begin: " + part);
                    temp = customerDao.findByName(part);
                    for (Customer c : temp) {
                        LOGGER.debug("temp: " + part + " " + c.getFirstname());
                    }
                    customerList.removeAll(temp);
                    for (Customer c : customerList) {
                        LOGGER.debug("Cu Remove: " + part + " " + c.getFirstname());
                    }

                    for (Customer c : customerList) {
                        LOGGER.debug("Cu after remove:  " + part + " " + c.getFirstname());
                    }
                    customerList.addAll(temp);
                    for (Customer c : customerList) {
                        LOGGER.debug("Cu added:  " + part + " " + c.getFirstname());
                    }
                    temp.clear();
                }

                return customerList;
            } else {
                return customerDao.findByName(name);
            }

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public Customer createNewCustomer(Customer customer) throws ServiceException {
        try {
            return customerDao.save(customer);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
    */
    @Override
    public void updateCustomer(Customer customer) throws ServiceException {
        try {
          /*  customerDao.updateCustomer(customer.getFirstname(), customer.getLastname(), customer.getAddress().getStreet(),
                    customer.getDateOfBirth(), customer.getId());*/
            LOGGER.debug("Firstname:" + customer.getFirstname() + ", id: "+ customer.getId()+ ", street: " + customer.getAddress().getStreet());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
