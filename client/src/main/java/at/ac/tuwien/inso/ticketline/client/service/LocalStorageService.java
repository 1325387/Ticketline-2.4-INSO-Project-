package at.ac.tuwien.inso.ticketline.client.service;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.dto.NewsDto;

import java.util.List;

/**
 * @author Lina Wang, 1326922
 */
public interface LocalStorageService {

    public void updateLocalStorage(String employee, List<NewsDto> news) throws ServiceException;

}
