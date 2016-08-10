package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.dto.NewsDto;

import java.util.List;

/**
 * The Interface NewsService.
 */
public interface NewsService {

    /**
     * Gets the news.
     *
     * @return the news
     * @throws ServiceException the service exception
     */
    public List<NewsDto> getNews() throws ServiceException;

    /**
     * Gets the news, that the given employee hasn't already read
     *
     * @param name name of employee
     * @return list of unread news
     * @throws ServiceException
     */
    public List<NewsDto> getUnreadNewsOfEmployee(String name) throws ServiceException;

    /**
     * Publish news.
     *
     * @param news the news
     * @return the integer
     * @throws ServiceException the service exception
     */
    public Integer publishNews(NewsDto news) throws ServiceException;

}
