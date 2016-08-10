package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.EmployeeDao;
import at.ac.tuwien.inso.ticketline.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * This class generates data for employees
 * 
 * @see at.ac.tuwien.inso.ticketline.model.Employee
 */
@Component
public class EmployeeGenerator implements DataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeGenerator.class);

    @Autowired
    private EmployeeDao dao;

    @Autowired
    private PasswordEncoder encoder;

	/**
	 * {@inheritDoc}
	 */
    public void generate() {
        LOGGER.info("+++++ Generate Employee Data +++++");
        String firstname = "Marvin";
        String lastname = "Jones";
        String username = "marvin";
        String password = "42";
        String pwHash = this.encoder.encode(password);
        Employee e1 = new Employee(firstname, lastname, username, pwHash);
        e1.setAttempts(0);
        dao.save(e1);

        Employee e2 = new Employee("Jane", "Doe", "janed", this.encoder.encode("24"));
        e2.setAttempts(0);
        dao.save(e2);

        Employee e3 = new Employee("Anthony", "Da", "anthony", this.encoder.encode("19"));
        e3.setAttempts(0);
        dao.save(e3);

    }

}
