package at.ac.tuwien.inso.ticketline.server.unittest.service;

import at.ac.tuwien.inso.ticketline.dao.EmployeeDao;
import at.ac.tuwien.inso.ticketline.model.Employee;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.implementation.SimpleEmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.LockedException;

import static org.mockito.Matchers.any;
import static org.junit.Assert.*;

/**
 * @author Lina Wang, 1326922
 */
public class EmployeeServiceTest {


    private SimpleEmployeeService employeeService = new SimpleEmployeeService();
    private Employee employee;

    @Before
    public void setUp() {
        employee = new Employee("Jane", "Doe", "janed", "24");
        employee.setId(1);
        employee.setAttempts(1);
    }

    @Test
    public void testUpdateAttempt() throws ServiceException {
        EmployeeDao employeeDao = Mockito.mock(EmployeeDao.class);
        employeeService.setEmployeeDao(employeeDao);
        Mockito.when(employeeDao.save(any(Employee.class))).thenReturn(employee);

        Employee e = employeeService.updateAttempt(employee, 2);
        assertTrue(e.getAttempts() == 2);
    }

    @Test(expected = LockedException.class)
    public void testUpdateAttemptFailed() throws ServiceException {
        EmployeeDao employeeDao = Mockito.mock(EmployeeDao.class);
        employeeService.setEmployeeDao(employeeDao);
        Mockito.when(employeeDao.save(any(Employee.class))).thenReturn(employee);

        Employee e = null;
        e = employeeService.updateAttempt(employee, 5);

        assertTrue(e.getAttempts() == 5);
    }
}
