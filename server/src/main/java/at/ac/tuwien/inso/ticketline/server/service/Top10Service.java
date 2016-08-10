package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.model.PerformanceType;
import at.ac.tuwien.inso.ticketline.model.Topten;
import at.ac.tuwien.inso.ticketline.model.ToptenPerformances;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.List;

/**
 * Service for {@link PerformanceType}
 *
 * @author Aysel Oeztuerk 0927011.
 */
public interface Top10Service {

    /**
     * Returns the top 10 shows of given perfromance type/category by count of sold tickets.
     *
     *
     * @param year
     * @param month
     *@param type of the performance  @return a list of topten entities
     * @throws ServiceException the service exception
     */
    public List<Topten> getTop10ShowsOfPerformanceType(int year, int month, PerformanceType type) throws ServiceException;

    /**
     * Returns the top 10 performances of given perfromance type/category by count of sold tickets.
     *
     * @param year
     * @param month
     * @param type of the performance  @return a list of topten entities
     * @throws ServiceException the service exception
     */
    public List<ToptenPerformances> getTop10PerformancesOfPerformanceType(int year, int month, PerformanceType type) throws ServiceException;

}
