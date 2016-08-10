package at.ac.tuwien.inso.ticketline.server.integrationtest.service;

import at.ac.tuwien.inso.ticketline.model.Show;
import at.ac.tuwien.inso.ticketline.model.Ticket;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.ShowService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;


/**
 * @author Lina Wang, 1326922
 */
public class ShowServiceIntegrationTest extends AbstractServiceIntegrationTest{

    @Autowired
    private ShowService showService;

    @Test
    public void testGetShow() throws ServiceException {
        Show show = showService.getShow(1);
        assertTrue(show.getId()==1);
        List<Show> shows = this.showService.getAllShows();
        assertTrue(shows.contains(show));
    }

    @Test
    public void testGetAllShows() throws ServiceException {
        List<Show> shows = showService.getAllShows();
        assertNotNull(shows);
        assertEquals(shows.size(),4);
    }

    @Test
    public void testGetAllShowsByPerformance() throws ServiceException {
        List<Show> shows = showService.getAllShowsByPerformance(1);
        assertNotNull(shows);
        assertEquals(shows.size(),2);
        assertTrue(shows.get(0).getPerformance().getId()==1);
        List<Show> allShows = this.showService.getAllShows();
        assertTrue(allShows.containsAll(shows));

    }

    @Test
    public void testGetShowByTicket() throws ServiceException {
        Show show = showService.getShowByTicket(1);
        List<Show> allShows = this.showService.getAllShows();
        assertTrue(show.getId()==1);
        assertTrue(allShows.contains(show));
    }

}
