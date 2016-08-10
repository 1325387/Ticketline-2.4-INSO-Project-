package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.EmployeeDao;
import at.ac.tuwien.inso.ticketline.dao.LocalStorageDao;
import at.ac.tuwien.inso.ticketline.dao.NewsDao;
import at.ac.tuwien.inso.ticketline.model.Employee;
import at.ac.tuwien.inso.ticketline.model.LocalStorage;
import at.ac.tuwien.inso.ticketline.model.News;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.rest.InformationController;
import at.ac.tuwien.inso.ticketline.server.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Implementation of the {@link at.ac.tuwien.inso.ticketline.server.service.NewsService} interface
 */
@Service
public class SimpleNewsService implements NewsService {

    @Autowired
    private NewsDao newsDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private LocalStorageDao localStorageDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleNewsService.class);


    /**
     * {@inheritDoc}
     */
    @Override
    public News getNews(Integer id) throws ServiceException {
        try {
            return newsDao.findOne(id);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public News save(News news) throws ServiceException {
        try {
            news.setSubmittedOn(new Date());
            return newsDao.save(news);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<News> getAllNews() throws ServiceException {
        try {
            return newsDao.findAllOrderBySubmittedOnAsc();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<News> getUnreadNews(String name) throws ServiceException {
        try {
            List<News> allNews = newsDao.findAllOrderBySubmittedOnAsc();
            List<News> readNews = new ArrayList<>();
            //Username is unique, so size of employee must be 1
            List<Employee> employee = employeeDao.findByUsername(name);
            List<LocalStorage> localStorages = localStorageDao.findByEmployeeId(employee.get(0).getId());
            //Get news of Storage
            for (LocalStorage l : localStorages) {
                if (l.getNewsId() != null) {
                    readNews.add(newsDao.findOne(l.getNewsId()));
                    LOGGER.debug("News ID: " + l.getNewsId());
                }
            }
            LOGGER.debug("Size before removing:  " + allNews.size());
            //Remove readnews from allnews
            for(News read : readNews) {
                Iterator it = allNews.iterator();
                while (it.hasNext()) {
                    News news = (News) it.next();
                    LOGGER.debug("Read news ID: " + read.getId() + " All News ID :" + news.getId());
                     if(read.getId() == news.getId()) {
                         it.remove();
                     }
                }
            }
            LOGGER.debug("Size after removing: " + allNews.size());
            return allNews;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    // -------------------- For Testing purposes --------------------

    /**
     * Sets the news dao.
     *
     * @param dao the new news dao
     */
    public void setNewsDao(NewsDao dao) {
        this.newsDao = dao;
    }
    public void setEmployeeDao(EmployeeDao dao) {
        this.employeeDao = dao;
    }
    public void setLocalStorageDao(LocalStorageDao dao) {
        this.localStorageDao = dao;
    }
    // -------------------- For Testing purposes --------------------
}
