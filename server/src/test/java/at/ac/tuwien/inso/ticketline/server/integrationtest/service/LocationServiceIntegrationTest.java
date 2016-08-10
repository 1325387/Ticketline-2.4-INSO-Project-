package at.ac.tuwien.inso.ticketline.server.integrationtest.service;

import at.ac.tuwien.inso.ticketline.model.Location;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.LocationService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Lina Wang, 1326922
 */
public class LocationServiceIntegrationTest extends AbstractServiceIntegrationTest {

    @Autowired
    private LocationService locationService;

    @Test
    public void testGetLocation() throws ServiceException {
        Location location = this.locationService.getLocation(1);
        assertTrue(location.getId()==1);
    }

    @Test
    public void testGetAllLocations() throws ServiceException {
        List<Location> locations = this.locationService.getAllLocations();
        assertNotNull(locations);
        assertEquals(locations.size(),4);
    }

    @Test
    public void testGetCities() throws ServiceException {
        List<String> cities = this.locationService.getCities();
        assertNotNull(cities);
        assertEquals(cities.size(),3);
    }

    @Test
    public void testGetPostalCodes() throws ServiceException {
        List<String> plz = this.locationService.getPostalCodes();
        assertNotNull(plz);
        assertEquals(plz.size(),4);
    }

}
