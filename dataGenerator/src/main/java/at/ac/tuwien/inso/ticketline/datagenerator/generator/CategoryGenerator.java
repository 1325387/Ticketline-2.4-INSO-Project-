package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.CategoryDao;
import at.ac.tuwien.inso.ticketline.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Lina Wang 1326922
 */
@Component
public class CategoryGenerator implements DataGenerator{

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryGenerator.class);

    @Autowired
    private CategoryDao categoryDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public void generate() {
        LOGGER.info("+++++ Generate Category Data +++++");

        categoryDao.save(new Category("Economy", "This is the cheapest category"));
        categoryDao.save(new Category("Standard", "This is the normal category"));
        categoryDao.save(new Category("VIP", "This is the best category"));

    }
}
