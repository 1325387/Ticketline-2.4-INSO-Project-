package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.LocationDao;
import at.ac.tuwien.inso.ticketline.model.Location;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link at.ac.tuwien.inso.ticketline.server.service.LocationService} interface
 *
 * Created by Aysel Oeztuerk on 04.05.2016.
 */
@Service
public class SimpleLocationService implements LocationService {

    @Autowired
    private LocationDao locationDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public Location getLocation(Integer id) throws ServiceException {
        try {
            return locationDao.findOne(id);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Location> getAllLocations() throws ServiceException {
        try {
            return locationDao.findAll();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getCities() throws ServiceException {
        try {
            return locationDao.getCities();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getPostalCodes() throws ServiceException {
        try {
            return locationDao.getPostalCodes();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }


}
