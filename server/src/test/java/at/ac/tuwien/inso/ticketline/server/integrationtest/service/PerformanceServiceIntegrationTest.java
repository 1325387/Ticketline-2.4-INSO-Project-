package at.ac.tuwien.inso.ticketline.server.integrationtest.service;

import at.ac.tuwien.inso.ticketline.model.Performance;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.PerformanceService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;


/**
 * @author Lina Wang, 1326922
 */
public class PerformanceServiceIntegrationTest extends  AbstractServiceIntegrationTest {

    @Autowired
    private PerformanceService performanceService;

    @Test
    public void testGetPerformance() throws ServiceException {
        Performance p = this.performanceService.getPerformance(1);
        assertTrue(p.getId()==1);
    }

    @Test
    public void testGetAllPerformances() throws ServiceException {
        List<Performance> performances = this.performanceService.getAllPerformances();
        assertNotNull(performances);
        assertTrue(performances.size()==2);
    }

    @Test
    public void testGetPerformanceOfShow() throws ServiceException {
        Performance p = this.performanceService.getPerformanceOfShow(1);
        List<Performance> allPerformances = this.performanceService.getAllPerformances();
        assertTrue(p.getId()==1);
        assertTrue(allPerformances.contains(p));
    }

    @Test
    public void testFilterPerformance() throws ServiceException {
        List<Performance> performances = this.performanceService.filterPerformances("2","null","null","null","null","null","null","null");
        List<Performance> allPerformances = this.performanceService.getAllPerformances();
        assertTrue(performances.size()==1);
        assertTrue(allPerformances.containsAll(performances));

    }

}
