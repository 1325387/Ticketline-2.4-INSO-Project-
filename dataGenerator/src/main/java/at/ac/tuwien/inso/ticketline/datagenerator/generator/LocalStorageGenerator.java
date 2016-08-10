package at.ac.tuwien.inso.ticketline.datagenerator.generator;

import at.ac.tuwien.inso.ticketline.dao.EmployeeDao;
import at.ac.tuwien.inso.ticketline.dao.LocalStorageDao;
import at.ac.tuwien.inso.ticketline.dao.NewsDao;
import at.ac.tuwien.inso.ticketline.dao.ShowDao;
import at.ac.tuwien.inso.ticketline.model.Employee;
import at.ac.tuwien.inso.ticketline.model.LocalStorage;
import at.ac.tuwien.inso.ticketline.model.News;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author Lina Wang, 1326922
 */
@Component
public class LocalStorageGenerator implements DataGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocalStorageGenerator.class);


    @Autowired
    private LocalStorageDao localStorageDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public void generate() {

        LOGGER.info("+++++ Generate LocalStorage Data +++++");

        LocalStorage ls1 = new LocalStorage();
        LocalStorage ls2 = new LocalStorage();
        LocalStorage ls3 = new LocalStorage();

        ls1.setEmployeeId(1);
        ls2.setEmployeeId(2);

        ls3.setEmployeeId(3);
        localStorageDao.save(ls1);
        localStorageDao.save(ls2);
        localStorageDao.save(ls3);

    }
}
