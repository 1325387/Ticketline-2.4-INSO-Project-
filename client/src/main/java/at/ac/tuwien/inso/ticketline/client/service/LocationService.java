package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;

import java.util.List;

/**
 * @author Sissi Zhan 1325880
 *
 * The Interface LocationRestClient
 */
public interface LocationService {

    /**
     * @return all cities
     */
    public List<String> getCities() throws ServiceException;

    /**
     * @return all postalcodes
     */
    public List<String> getPostalCodes() throws ServiceException;
}
