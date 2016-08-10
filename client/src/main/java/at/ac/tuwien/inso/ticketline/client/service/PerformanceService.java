package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.dto.PerformanceFilterDto;
import at.ac.tuwien.inso.ticketline.dto.PerformanceDto;

import java.sql.Date;
import java.util.List;

/**
 * @author Sissi Zhan 1325880
 *
 * The Interface PerformanceService
 */
public interface PerformanceService {

    /**
     * @author Sissi Zhan 1325880
     *
     * Gets all performances
     *
     * @return a list of performances
     * @throws ServiceException
     */
    public List<PerformanceDto> getAll() throws ServiceException;

    /**
     * @author Sissi Zhan 1325880
     *
     * Gets the performance of a specific show
     *
     * @param showId the show
     * @return a performance
     * @throws ServiceException
     */
    public PerformanceDto getOfShow(int showId) throws ServiceException;

    /**
     * @author Sissi Zhan 1325880
     *
     * Filters performances after given factors
     *
     *
     * @return a list of performances that have shows with the given factors
     * @throws ServiceException
     */
    public List<PerformanceDto> filterPerformances(
            String performanceName, String date, String town, String plz, String time, String performanceType,
            String price, String artist
    ) throws ServiceException;

}
