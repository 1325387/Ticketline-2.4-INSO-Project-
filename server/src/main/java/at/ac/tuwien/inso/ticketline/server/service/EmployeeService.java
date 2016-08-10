package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.model.Employee;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.List;

/**
 * @author Lina Wang 1326922
 */
public interface EmployeeService {

    /**
     * @author Lina Wang 1326922
     *
     * Updates the login attempt of user
     *
     * @param employee the user
     * @return employee
     * @throws ServiceException
     */
    public Employee updateAttempt(Employee employee, Integer attempt) throws ServiceException;

    /**
     * @author Lina Wang 1326922
     *
     * Finds user by username
     *
     * @param username username of user
     * @return list of users with given username
     * @throws ServiceException
     */
    public List<Employee> findByUsername(String username) throws ServiceException;
}
