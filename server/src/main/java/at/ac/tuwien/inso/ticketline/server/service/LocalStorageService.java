package at.ac.tuwien.inso.ticketline.server.service;

import at.ac.tuwien.inso.ticketline.model.LocalStorage;
import at.ac.tuwien.inso.ticketline.model.News;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;

import java.util.List;

/**
 * @author Lina Wang, 1326922
 */

public interface LocalStorageService {

    /**
     * Updates a localstorage of employee
     *
     * @param employee the employee
     * @param news the read news
     * @return a list of localstorages
     * @throws ServiceException
     */
    public List<LocalStorage> updateLocalStorage(String employee, List<News> news) throws ServiceException;
}
