package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.RowDao;
import at.ac.tuwien.inso.ticketline.model.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Lina Wang 1326922
 *
 */
@Component
public class RowGenerator implements DataGenerator{


    private static final Logger LOGGER = LoggerFactory.getLogger(RowGenerator.class);

    @Autowired
    private RowDao rowDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public void generate() {
        LOGGER.info("+++++ Generate Row Data +++++");

        rowDao.save(new Row("Row A", "This is the first row", 1));
        rowDao.save(new Row("Row B", "This is the second row", 2));
        rowDao.save(new Row("Row C", "This is the third row", 3));
        rowDao.save(new Row("Row D", "This is the fourth row", 4));
        rowDao.save(new Row("Row E", "This is the fifth row", 5));

    }
}
