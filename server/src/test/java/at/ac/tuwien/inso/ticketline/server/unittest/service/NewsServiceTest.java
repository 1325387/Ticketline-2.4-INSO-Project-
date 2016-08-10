package at.ac.tuwien.inso.ticketline.server.unittest.service;

import at.ac.tuwien.inso.ticketline.dao.EmployeeDao;
import at.ac.tuwien.inso.ticketline.dao.LocalStorageDao;
import at.ac.tuwien.inso.ticketline.dao.NewsDao;
import at.ac.tuwien.inso.ticketline.model.Employee;
import at.ac.tuwien.inso.ticketline.model.LocalStorage;
import at.ac.tuwien.inso.ticketline.model.News;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.implementation.SimpleNewsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;

public class NewsServiceTest {

    private SimpleNewsService service = null;

    private List<News> news = null;
    private News n1;

    private List<Employee> employeeDB = new ArrayList<>();
    private Employee employee;

    private List<LocalStorage> localStorageDB = new ArrayList<>();
    private LocalStorage localStorage;

    @Before
    public void setUp() {
        service = new SimpleNewsService();
        news = new ArrayList<>();
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(2013, Calendar.JUNE, 1);
        n1 = new News(1, gc.getTime(), "first Title", "first newstext");
        news.add(n1);
        News n2 = new News(2, gc.getTime(), "Second Title", "Hudel Dudel");
        news.add(n2);
        gc.set(2013, Calendar.AUGUST, 4);
        News n3 = new News(3, gc.getTime(), "Bi Ba", "Fischers Fritz fischt frische Fische");
        news.add(n3);
        News n4 = new News(4, gc.getTime(), "Änderungsmitteilung", "Alles muss raus");
        news.add(n4);
        gc.set(2013, Calendar.SEPTEMBER, 15);
        News n5 = new News(5, gc.getTime(), "It's the last News", "Nix neues im Westen");
        news.add(n5);

        employee = new Employee("Jane", "Doe", "janed", "24");
        employee.setId(1);
        employee.setAttempts(1);
        employeeDB.add(employee);

        localStorage = new LocalStorage();
        localStorage.setId(1);
        localStorage.setNewsId(1);
        localStorage.setEmployeeId(1);
        localStorageDB.add(localStorage);
    }

    @Test
    public void testGetAll() throws ServiceException {
        NewsDao dao = Mockito.mock(NewsDao.class);
        Mockito.when(dao.findAllOrderBySubmittedOnAsc()).thenReturn(news);
        service.setNewsDao(dao);

        assertEquals(5, service.getAllNews().size());

    }

    @Test
    public void testGetAll_shouldThrowServiceException() {
        NewsDao dao = Mockito.mock(NewsDao.class);
        Mockito.when(dao.findAllOrderBySubmittedOnAsc()).thenThrow(new RuntimeException("no db"));
        service.setNewsDao(dao);

        try {
            service.getAllNews();
            fail("ServiceException not thrown");
        } catch (ServiceException e) {
            assertNotNull(e.getMessage());
        }
    }

    @Test
    public void testGetUnreadNews() throws ServiceException {
        NewsDao newsDao = Mockito.mock(NewsDao.class);
        EmployeeDao employeeDao = Mockito.mock(EmployeeDao.class);
        LocalStorageDao localStorageDao = Mockito.mock(LocalStorageDao.class);
        service.setNewsDao(newsDao);
        service.setEmployeeDao(employeeDao);
        service.setLocalStorageDao(localStorageDao);

        Mockito.when(newsDao.findAllOrderBySubmittedOnAsc()).thenReturn(news);
        Mockito.when(employeeDao.findByUsername(any(String.class))).thenReturn(employeeDB);
        Mockito.when(localStorageDao.findByEmployeeId(any(Integer.class))).thenReturn(localStorageDB);
        Mockito.when(newsDao.findOne(any(Integer.class))).thenReturn(n1);

        List<News> unreadNews = service.getUnreadNews("janed");
        assertNotNull(unreadNews);
        assertEquals(unreadNews.size(),4);
        assertFalse(unreadNews.contains(n1));

    }

}
