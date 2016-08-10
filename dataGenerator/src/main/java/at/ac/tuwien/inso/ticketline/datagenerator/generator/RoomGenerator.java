package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.LocationDao;
import at.ac.tuwien.inso.ticketline.dao.RoomDao;
import at.ac.tuwien.inso.ticketline.model.Location;
import at.ac.tuwien.inso.ticketline.model.Room;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This class generates data for room.
 *
 * Created by Aysel Oeztuerk on 02.05.2016.
 */
@Component
public class RoomGenerator implements DataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomGenerator.class);


    @Autowired
    private RoomDao roomDao;
    @Autowired
    private LocationDao locationDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public void generate() {
        LOGGER.info("+++++ Generate Room Data +++++");

        int counter = 1;
        List<Location> locations = this.locationDao.findAll();

        //Generating Cinema halls
        for (int i = 1; i <= locations.size(); i++) {
            roomDao.save(new Room("Saal " + counter, "Immersive Sound, RealD 3D", locationDao.findOne(i)));
            counter++;
            roomDao.save(new Room("Saal " + counter, "Immersive Sound, RealD 3D", locationDao.findOne(i)));
            counter++;
            roomDao.save(new Room("Saal " + counter, "Immersive Sound, RealD 3D", locationDao.findOne(i)));
            counter++;
        }

        //Generating Concert halls
        for(int j = 1; j <= 5; j++) {
            roomDao.save(new Room("Halle " + j, "Stadthalle", locationDao.findOne(j)));
        }

    }
}
