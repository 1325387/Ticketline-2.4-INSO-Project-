package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.dto.GalleryDto;
import at.ac.tuwien.inso.ticketline.dto.SeatDto;

import java.util.List;
import java.util.Map;

/**
 * @author Lina Wang 1326922
 */
public interface GalleryService {

    public List<GalleryDto> getallOfShow(Integer showId) throws ServiceException;

}

