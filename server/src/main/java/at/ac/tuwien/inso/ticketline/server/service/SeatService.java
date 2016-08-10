package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.dto.SeatDto;
import at.ac.tuwien.inso.ticketline.model.Seat;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.List;

/**
 * @author Sissi Zhan 1325880
 */
public interface SeatService {

    /**
     * @author Sissi Zhan 1325880
     *
     * gets all seats from a specific show
     * @param showId id of the show from which its seats should be returned
     * @return all seats from a show
     * @throws ServiceException
     */
    public List<Seat> getAllOfShow(Integer showId) throws ServiceException;

    /**
     * @author Lina Wang 1326922
     *
     * @param galleryId the gallery ID
     * @return all seats in a gallery
     * @throws ServiceException
     */
    public List<Seat> getAllOfGallery(Integer galleryId) throws ServiceException;

}