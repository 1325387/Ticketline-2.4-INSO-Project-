package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.dto.SeatDto;

import java.util.List;

/**
 * @author Raphael Schotola 1225193
 */
public interface SeatService {
    /**
     * @author Raphael Schotola 1225193
     *
     * @param showId the ID of the show
     * @return all seats of the given show
     * @throws ServiceException
     */
    public List<SeatDto> getAllOfShow(Integer showId) throws ServiceException;


    /**
     * @author Lina Wang 1326922
     *
     * @param galleryId the ID of the gallery
     * @return all seats in given gallery
     * @throws ServiceException
     */
    public List<SeatDto> getAllOfGallery(Integer galleryId) throws ServiceException;
}
