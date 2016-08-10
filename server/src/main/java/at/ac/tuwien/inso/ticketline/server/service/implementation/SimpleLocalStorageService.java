package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.EmployeeDao;
import at.ac.tuwien.inso.ticketline.dao.LocalStorageDao;
import at.ac.tuwien.inso.ticketline.model.Employee;
import at.ac.tuwien.inso.ticketline.model.LocalStorage;
import at.ac.tuwien.inso.ticketline.model.News;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.LocalStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lina Wang, 1326922
 */
@Service
public class SimpleLocalStorageService implements LocalStorageService{

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private LocalStorageDao localStorageDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LocalStorage> updateLocalStorage(String employee, List<News> news) throws ServiceException {
        try {
            List<LocalStorage> result = new ArrayList<>();
            List<Employee> employees = employeeDao.findByUsername(employee);
            Integer employeeId = employees.get(0).getId();

            for(News n : news) {
                LocalStorage localStorage = new LocalStorage();
                localStorage.setEmployeeId(employeeId);
                localStorage.setNewsId(n.getId());
                result.add(localStorageDao.save(localStorage));
            }

            return result;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    // -------------------- For Testing purposes --------------------
    public void setEmployeeDao(EmployeeDao dao) {
        this.employeeDao = dao;
    }

    public void setLocalStorageDao(LocalStorageDao dao) {
        this.localStorageDao = dao;
    }


}

