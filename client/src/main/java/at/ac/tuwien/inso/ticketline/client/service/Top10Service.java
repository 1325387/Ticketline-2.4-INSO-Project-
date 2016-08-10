package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.dto.ToptenDto;
import at.ac.tuwien.inso.ticketline.dto.ToptenPerformancesDto;

import java.util.List;

/**
 * @author Aysel Oeztuerk 0927011
 *
 * the Interface Top10Service.
 */
public interface Top10Service {

    /**
     * Returns the top 10 shows of given performance type/category by count of sold tickets.
     *
     * @param performanceType type of the performance
     * @param year of the performance
     * @param month of the performance
     * @return a list of top 10 shows
     * @throws ServiceException the service exception
     */
    List<ToptenDto> getTop10Shows(int year, int month, String performanceType) throws ServiceException;
    /**
     * Returns the top 10 performances of given performance type/category by count of sold tickets.
     *
     * @param performanceType type of the performance
     * @param year of the performance
     * @param month of the performance
     * @return a list of top 10 performances
     * @throws ServiceException the service exception
     */
    List<ToptenPerformancesDto> getTop10Performances(int year, int month, String performanceType) throws ServiceException;

}
