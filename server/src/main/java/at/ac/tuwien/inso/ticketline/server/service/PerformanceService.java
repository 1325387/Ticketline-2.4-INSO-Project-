package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.model.Performance;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.sql.Date;
import java.util.List;

/**
 * Created by Aysel Oeztuerk on 04.05.2016.
 */
public interface PerformanceService {
    /**
     * Returns the performance object identified by the given id.
     *
     * @param id of the performance object
     * @return the performance object
     * @throws ServiceException the service exception
     */
    public Performance getPerformance(Integer id) throws ServiceException;

    /**
     * Returns a collection of all performances.
     *
     * @return java.util.List
     * @throws ServiceException the service exception
     */
    public List<Performance> getAllPerformances() throws ServiceException;

    /**
     * @author Sissi Zhan 1325880
     *
     * @param showId the ID of the show
     * @return the Performance of the show
     * @throws ServiceException
     */
    public Performance getPerformanceOfShow(int showId) throws ServiceException;

    /**
     * @author Sissi Zhan 1325880
     *
     * @return a list with filtered performances
     * @throws ServiceException
     */
    public List<Performance> filterPerformances(
            String performanceName, String date, String town, String plz, String time, String performanceType,
            String price, String artist
    ) throws ServiceException;
}
