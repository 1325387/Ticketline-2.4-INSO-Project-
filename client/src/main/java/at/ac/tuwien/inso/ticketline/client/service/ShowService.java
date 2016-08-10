package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.dto.ShowDto;
import at.ac.tuwien.inso.ticketline.dto.TicketDto;

import java.util.List;

/**
 * @author Sissi Zhan 1325880
 *
 * The interface for ShowService
 */
public interface ShowService {

    /**
     * @author Lina Wang 1326922
     *
     * Gets all shows
     *
     * @return a list of shows
     * @throws ServiceException
     */
    public List<ShowDto> getAll() throws ServiceException;

    /**
     * @author Lina Wang 1326922
     *
     * Gets all shows from a specific performance
     *
     * @param performanceId the ID of performance
     * @return all shows of the given performance
     * @throws ServiceException
     */
    public List<ShowDto> getAllOfPerformance(int performanceId) throws ServiceException;

    public ShowDto getOfTicket(TicketDto ticket) throws ServiceException;
}