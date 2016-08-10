package at.ac.tuwien.inso.ticketline.server.unittest.service;

import at.ac.tuwien.inso.ticketline.dao.EmployeeDao;
import at.ac.tuwien.inso.ticketline.dao.LocalStorageDao;
import at.ac.tuwien.inso.ticketline.model.Employee;
import at.ac.tuwien.inso.ticketline.model.LocalStorage;
import at.ac.tuwien.inso.ticketline.model.News;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.LocalStorageService;
import at.ac.tuwien.inso.ticketline.server.service.implementation.SimpleLocalStorageService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.junit.Assert.*;


/**
 * @author Lina Wang,1326922
 */
public class LocalStorageServiceTest {


    private SimpleLocalStorageService localStorageService = new SimpleLocalStorageService();

    private LocalStorage localStorage;

    private List<Employee> employeeDB = new ArrayList<>();
    private Employee employee;

    private List<News> readNewsDB = new ArrayList();
    private News readNews;


    @Before
    public void setUp() {

        employee = new Employee("Jane", "Doe", "janed", "24");
        employee.setId(1);
        employee.setAttempts(0);
        employeeDB.add(employee);

        localStorage = new LocalStorage();
        localStorage.setEmployeeId(employee.getId());
        localStorage.setNewsId(1);

        readNews = new News();
        readNews.setId(1);
        readNews.setSubmittedOn(new Date());
        readNews.setTitle("Title");
        readNews.setNewsText("Text");
        readNewsDB.add(readNews);
    }

    @Test
    public void testUpdateLocalStorage() throws ServiceException {
        LocalStorageDao localStorageDao = Mockito.mock(LocalStorageDao.class);
        EmployeeDao employeeDao = Mockito.mock(EmployeeDao.class);
        localStorageService.setLocalStorageDao(localStorageDao);
        localStorageService.setEmployeeDao(employeeDao);

        Mockito.when(employeeDao.findByUsername(any(String.class))).thenReturn(employeeDB);
        Mockito.when(localStorageDao.save(any(LocalStorage.class))).thenReturn(localStorage);

        List<LocalStorage> storage = localStorageService.updateLocalStorage(employee.getUsername(), readNewsDB);
        assertNotNull(storage);
        assertTrue(storage.size() == 1);
        assertTrue(storage.get(0).getNewsId() == readNews.getId());
        assertTrue(storage.get(0).getEmployeeId() == employee.getId());


    }


}
