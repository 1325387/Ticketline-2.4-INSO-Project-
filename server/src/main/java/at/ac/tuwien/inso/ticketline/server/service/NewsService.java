package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.model.News;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.List;

/**
 * Service for {@link at.ac.tuwien.inso.ticketline.model.News}
 */
public interface NewsService {

    /**
     * Returns the news object identified by the given id.
     *
     * @param id of the news object
     * @return the news object
     * @throws ServiceException the service exception
     */
    public News getNews(Integer id) throws ServiceException;


    /**
     * Saves the given news object and returns the saved entity.
     *
     * @param news object to persist
     * @return the saved entity
     * @throws ServiceException the service exception
     */
    public News save(News news) throws ServiceException;

    /**
     * Returns a collection of all news.
     *
     * @return java.util.List
     * @throws ServiceException the service exception
     */
    public List<News> getAllNews() throws ServiceException;

    /**
     * Returns a list of all unread news of given employee
     *
     * @param name name of employee
     * @return a list of unread news
     * @throws ServiceException
     */
    public List<News> getUnreadNews(String name) throws ServiceException;
}

