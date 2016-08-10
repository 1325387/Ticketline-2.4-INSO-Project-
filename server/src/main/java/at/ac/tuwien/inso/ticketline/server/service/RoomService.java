package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.List;

/**
 * Service for {@link at.ac.tuwien.inso.ticketline.model.Room}
 *
 * Created by Aysel Oeztuerk on 04.05.2016.
 */
public interface RoomService {
    /**
     * Returns the room object identified by the given id.
     *
     * @param id of the room object
     * @return the room object
     * @throws ServiceException the service exception
     */
    public Room getRoom(Integer id) throws ServiceException;

    /**
     * Returns a collection of all rooms.
     *
     * @return java.util.List
     * @throws ServiceException the service exception
     */
    public List<Room> getAllRooms() throws ServiceException;
}
