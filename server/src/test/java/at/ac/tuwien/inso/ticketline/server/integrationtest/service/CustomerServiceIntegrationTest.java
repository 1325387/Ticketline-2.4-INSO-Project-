package at.ac.tuwien.inso.ticketline.server.integrationtest.service;

import at.ac.tuwien.inso.ticketline.model.Customer;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.CustomerService;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Lina Wang, 1326922 and Aysel Oeztuerk, 0927011
 */
public class CustomerServiceIntegrationTest extends AbstractServiceIntegrationTest{

    @Autowired
    private CustomerService customerService;

    @Test
    public void testGetCustomer() throws ServiceException {
        Customer customer = this.customerService.getCustomer(1);
        assertTrue(customer.getId()==1);
    }

    @Test
    public void testGetAllCustomers() throws ServiceException {
        List<Customer> customers = this.customerService.getAllCustomers();
        assertNotNull(customers);
        assertEquals(customers.size(),4);
    }

    @Test
    public void getCustomersByName() throws ServiceException {
        List<Customer> customers = this.customerService.getCustomersByName("mou");
        assertNotNull(customers);

        assertEquals(customers.get(0).getFirstname(),"Anonymous");
        assertEquals(customers.get(0).getLastname(),"Anonymous");
    }

    @Test
    public void getCustomersByMultipleName() throws ServiceException {
        List<Customer> customers = this.customerService.getCustomersByName("mou obe");
        assertNotNull(customers);

        assertEquals(customers.get(0).getFirstname(),"Anonymous");
        assertEquals(customers.get(1).getLastname(),"Obermayer");
    }

    @Test
    public void getCustomersBySpace() throws ServiceException {
        List<Customer> customers = this.customerService.getCustomersByName("   ");
        assertTrue(customers.isEmpty());
    }
}
