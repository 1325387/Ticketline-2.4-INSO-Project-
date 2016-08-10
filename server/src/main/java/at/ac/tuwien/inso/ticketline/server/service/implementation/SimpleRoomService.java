package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.RoomDao;
import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link @link at.ac.tuwien.inso.ticketline.server.service.RoomService} interface
 *
 * Created by Aysel Oeztuerk on 04.05.2016.
 */
@Service
public class SimpleRoomService implements RoomService {

    @Autowired
    private RoomDao roomDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public Room getRoom(Integer id) throws ServiceException {
        try {
            return roomDao.findOne(id);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Room> getAllRooms() throws ServiceException {
        try {
            return roomDao.findAll();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
