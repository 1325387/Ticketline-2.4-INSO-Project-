package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.dto.EmployeeDto;

/**
 * @author jayson on 27-Jun-16.
 */
public interface EmployeeService {

    /**
     * @author Jayson Asenjo 1325387
     *
     * @param username
     * @param firstname
     * @param lastname
     * @return
     * @throws ServiceException
     */
    public EmployeeDto getEmployee(String username, String firstname, String lastname) throws ServiceException;
}
