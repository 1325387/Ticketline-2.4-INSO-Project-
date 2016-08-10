package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.EmployeeDao;
import at.ac.tuwien.inso.ticketline.model.Employee;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.security.AuthenticationHandler;
import at.ac.tuwien.inso.ticketline.server.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Lina Wang, 1326922
 */
@Service
public class SimpleEmployeeService implements EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEmployeeService.class);


    /**
     * {@inheritDoc}
     */
    @Override
    public Employee updateAttempt(Employee employee, Integer attempt) throws ServiceException {
        Employee updatedEmployee;

        try {
            LOGGER.debug("Update employee " + employee.getUsername());
            employee.setAttempts(attempt);
            updatedEmployee = employeeDao.save(employee);

        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }

        if (employee.getAttempts() >= 5) {
            LOGGER.debug("User is locked");
            throw new LockedException("User " + employee.getUsername() + " is locked");
        }
        return updatedEmployee;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Employee> findByUsername(String username) throws ServiceException {
        try {
            return employeeDao.findByUsername(username);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    // -------------------- For Testing purposes --------------------
    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }
}
