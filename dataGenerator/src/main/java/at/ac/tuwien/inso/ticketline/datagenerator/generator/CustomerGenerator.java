package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.CustomerDao;
import at.ac.tuwien.inso.ticketline.model.Address;
import at.ac.tuwien.inso.ticketline.model.Customer;
import at.ac.tuwien.inso.ticketline.model.Gender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.EnumSet;
import java.util.GregorianCalendar;

/**
 * @author Lina Wang 1326922
 */
@Repository
public class CustomerGenerator implements DataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerGenerator.class);


    @Autowired
    private CustomerDao customerDao;

    @Override
    public void generate() {

        LOGGER.info("+++++ Generate Customer Data +++++");

        Customer anonymous = new Customer();
        String firstname = "Anonymous";
        String lastname = "Anonymous";
        String username = "?";
        String phoneNumber = "00431199887";
        Address address = new Address();
        address.setStreet("Utopia-Straße");

        anonymous.setFirstname(firstname);
        anonymous.setLastname(lastname);
        anonymous.setUsername(username);
        anonymous.setAddress(address);
        anonymous.setDateOfBirth(GregorianCalendar.from(ZonedDateTime.now().minusYears(22)).getTime());
        anonymous.setGender(Gender.MALE);
        anonymous.setPhoneNumber(phoneNumber);
        customerDao.save(anonymous);

        char alphabet = 'A';
        for (int i = 2; i <= 27; i++) {

            int age = i;
            if (i < 14) {
                age += 14;
            } else {
                age += 20;
            }

            Customer customer = new Customer();

            Address address1 = new Address();
            address1.setStreet("Utopia-Straße Nr." + i);

            customer.setFirstname("firstname" + alphabet);
            customer.setLastname("lastname" + alphabet);
            customer.setUsername("username" + alphabet);
            customer.setAddress(address);
            customer.setDateOfBirth(GregorianCalendar.from(ZonedDateTime.now().minusYears(age)).getTime());
            customer.setPhoneNumber("1234567890");

            if (i <= 15) {
                customer.setGender(Gender.MALE);
            } else {
                customer.setGender(Gender.FEMALE);
            }
            customerDao.save(customer);
            alphabet++;
        }
    }
}
