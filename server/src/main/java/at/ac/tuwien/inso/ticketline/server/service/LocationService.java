package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.model.Location;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.List;

/**
 *  Service for {@link at.ac.tuwien.inso.ticketline.model.Location}
 *
 * Created by Aysel Oeztuerk on 04.05.2016.
 */
public interface LocationService {
    /**
     * Returns the location object identified by the given id.
     *
     * @param id of the location object
     * @return the location object
     * @throws ServiceException the service exception
     */
    public Location getLocation(Integer id) throws ServiceException;

    /**
     * Returns a collection of all locations.
     *
     * @return java.util.List
     * @throws ServiceException the service exception
     */
    public List<Location> getAllLocations() throws ServiceException;

    public List<String> getCities() throws ServiceException;

    public List<String> getPostalCodes() throws ServiceException;
}
