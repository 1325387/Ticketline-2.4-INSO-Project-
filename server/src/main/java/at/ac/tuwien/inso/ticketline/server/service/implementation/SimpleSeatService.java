package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.SeatDao;
import at.ac.tuwien.inso.ticketline.dto.SeatDto;
import at.ac.tuwien.inso.ticketline.model.Seat;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.SeatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sissi Zhan 1325880
 */
@Service
public class SimpleSeatService implements SeatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleSeatService.class);

    @Autowired
    private SeatDao seatDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Seat> getAllOfShow(Integer showId) throws ServiceException {
        try {
            return this.seatDao.findAllOf(showId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Seat> getAllOfGallery(Integer galleryId) throws ServiceException {
        try {
            return this.seatDao.findAllByGallery(galleryId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
