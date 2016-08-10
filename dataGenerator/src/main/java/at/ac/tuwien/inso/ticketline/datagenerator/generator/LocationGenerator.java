package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.LocationDao;
import at.ac.tuwien.inso.ticketline.model.Address;
import at.ac.tuwien.inso.ticketline.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class generates data for location.
 *
 * Created by Aysel Oeztuerk on 02.05.2016.
 */
@Component
public class LocationGenerator implements DataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationGenerator.class);

    @Autowired
    private LocationDao locationDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public void generate() {
        LOGGER.info("+++++ Generate Location Data +++++");

        char alphabet = 'A';
        int counter = 1;
        //Currently creating 10 locations
        for (int i = 1; i <= 5; i++) {
            Address addy = new Address("Imaginary Street " + i, "1" + (i+10) + "0", "Stadt "+(alphabet), "Österreich");
            locationDao.save(new Location("Location " + counter, "Typ " + i, "Besitzer " + i, addy));
            counter++;
            locationDao.save(new Location("Location " + counter, "Typ " + i, "Besitzer " + i, addy));
            counter++;
            alphabet++;
        }
    }
}
