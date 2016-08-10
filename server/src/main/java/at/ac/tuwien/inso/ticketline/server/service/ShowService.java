package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.model.Show;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.List;

/**
 * Created by Aysel Oeztuerk on 04.05.2016.
 */
public interface ShowService {
    /**
     * Returns the show object identified by the given id.
     *
     * @param id of the show object
     * @return the show object
     * @throws ServiceException the service exception
     */
    public Show getShow(Integer id) throws ServiceException;

    /**
     * Returns a collection of all shows.
     *
     * @return java.util.List
     * @throws ServiceException the service exception
     */
    public List<Show> getAllShows() throws ServiceException;

    /**
     * Returns a collection of all shows filtered by performance id.
     *
     * @param id of the performance
     * @return java.util.List
     * @throws ServiceException the service exception
     */
    public List<Show> getAllShowsByPerformance(Integer id) throws ServiceException;

    /**
     * TODO
     * @param id
     * @return
     * @throws ServiceException
     */
    public Show getShowByTicket(Integer id) throws ServiceException;
}
