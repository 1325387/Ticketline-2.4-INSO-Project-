package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO for {@link at.ac.tuwien.inso.ticketline.model.Room}
 *
 * Created by Aysel Oeztuerk on 02.05.2016.
 */
@Repository
public interface RoomDao extends JpaRepository<Room, Integer> {
}
