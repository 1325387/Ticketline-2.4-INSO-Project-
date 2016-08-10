package at.ac.tuwien.inso.ticketline.server.integrationtest.service;

import at.ac.tuwien.inso.ticketline.model.Room;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.RoomService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;
/**
 * @author Lina Wang,1326922
 */
public class RoomServiceIntegrationTest extends AbstractServiceIntegrationTest{

    @Autowired
    private RoomService roomService;

    @Test
    public void testGetRoom() throws ServiceException {
        Room room = this.roomService.getRoom(1);
        assertTrue(room.getId()==1);
        List<Room> rooms = this.roomService.getAllRooms();
        assertTrue(rooms.contains(room));
    }

    @Test
    public void testGetAllRooms() throws ServiceException {
        List<Room> rooms = this.roomService.getAllRooms();
        assertNotNull(rooms);
        assertEquals(rooms.size(),2);
    }
}
