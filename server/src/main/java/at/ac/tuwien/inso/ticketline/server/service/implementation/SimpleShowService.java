package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.PerformanceDao;
import at.ac.tuwien.inso.ticketline.dao.ShowDao;
import at.ac.tuwien.inso.ticketline.model.Performance;
import at.ac.tuwien.inso.ticketline.model.Show;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.ShowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link at.ac.tuwien.inso.ticketline.server.service.ShowService} interface
 *
 * Created by Aysel Oeztuerk on 04.05.2016.
 */
@Service
public class SimpleShowService implements ShowService {


    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleShowService.class);

    @Autowired
    private ShowDao showDao;

    @Autowired
    private PerformanceDao performanceDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public Show getShow(Integer id) throws ServiceException {
        try {
            return showDao.findOne(id);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Show> getAllShows() throws ServiceException {
        try {
            return showDao.findAll();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Show> getAllShowsByPerformance(Integer id) throws ServiceException {
        try {
            LOGGER.debug("Performance ID:" + id);
            return showDao.findByPerformanceId(id);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Show getShowByTicket(Integer id) throws ServiceException {
        try {
            return showDao.findByTicketId(id);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
