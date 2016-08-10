package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.ShowDao;
import at.ac.tuwien.inso.ticketline.model.PerformanceType;
import at.ac.tuwien.inso.ticketline.model.Topten;
import at.ac.tuwien.inso.ticketline.model.ToptenPerformances;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.Top10Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link Top10Service} interface
 *
 * Created by Aysel Oeztuerk 0927011.
 */
@Service
public class SimpleTop10Service implements Top10Service {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTop10Service.class);

    @Autowired
    private ShowDao top10Dao;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Topten> getTop10ShowsOfPerformanceType(int year, int month, PerformanceType type) throws ServiceException {
        String stType = type.toString();
        try {
            List<Topten> top10List = top10Dao.getTop10ShowsOfPerformanceType(year, month, type);

            for(Topten t: top10List){
                LOGGER.debug("Show: " + t.getEventID() + " Tickets sold: " + t.getSoldTickets());
            }
            return top10List;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ToptenPerformances> getTop10PerformancesOfPerformanceType(int year, int month, PerformanceType type) throws ServiceException {
        String stType = type.toString();
        try {
            List<ToptenPerformances> top10List = top10Dao.getTop10PerformancesOfPerformanceType(year, month, type);

            for(ToptenPerformances t: top10List){
                LOGGER.debug("Show: " + t.getEventID() + " Tickets sold: " + t.getSoldTickets());
            }
            return top10List;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
