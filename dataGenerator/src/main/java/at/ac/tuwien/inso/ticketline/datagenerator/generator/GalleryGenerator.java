package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.GalleryDao;
import at.ac.tuwien.inso.ticketline.model.Gallery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Lina Wang 1326922
 */
@Component
public class GalleryGenerator implements DataGenerator{


    private static final Logger LOGGER = LoggerFactory.getLogger(GalleryGenerator.class);

    @Autowired
    private GalleryDao galleryDao;
    /**
     * {@inheritDoc}
     */
    @Override
    public void generate() {
        LOGGER.info("+++++ Generate Gallery Data +++++");


        galleryDao.save(new Gallery("A", "This is side gallery", 1));
        galleryDao.save(new Gallery("B", "This is middle gallery", 2));
        galleryDao.save(new Gallery("C", "This is back gallery", 3));

    }
}
