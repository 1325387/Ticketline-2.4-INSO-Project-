package at.ac.tuwien.inso.ticketline.server.service.implementation;

import at.ac.tuwien.inso.ticketline.dao.PerformanceDao;
import at.ac.tuwien.inso.ticketline.dao.ShowDao;
import at.ac.tuwien.inso.ticketline.model.Performance;
import at.ac.tuwien.inso.ticketline.model.PerformanceType;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.PerformanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementation of the {@link at.ac.tuwien.inso.ticketline.server.service.PerformanceService} interface
 *
 * Created by Aysel Oeztuerk on 04.05.2016.
 */
@Service
public class SimplePerformanceService implements PerformanceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimplePerformanceService.class);
    @Autowired
    private PerformanceDao performanceDao;
    @Autowired
    private ShowDao showDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public Performance getPerformance(Integer id) throws ServiceException {
        try {
            return performanceDao.findOne(id);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Performance> getAllPerformances() throws ServiceException {
        try {
            return performanceDao.findAll();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Performance getPerformanceOfShow(int showId) throws ServiceException {
        try {
            return performanceDao.findOfShow(showId);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * @author Sissi Zhan 1325880
     *
     * @throws ServiceException
     */
    @Override
    public List<Performance> filterPerformances(
            String performanceName, String date, String town, String plz, String time, String performanceType,
            String price, String artist
    ) throws ServiceException {

        try {
            List<Performance> resultList;

            PerformanceType type = null;
            int p_price = 0;
            String p_date = null;
            String p_time = null;

            if (!(performanceName.equals("null"))) {
                performanceName = replaceUnderscores(performanceName);
                if (performanceName.equals("")) {
                    performanceName = "null";
                }
            } else { performanceName = null; }

            if (!(town.equals("null"))) {
                town = replaceUnderscores(town);
            }  else { town = null; }

            if (!(plz.equals("null"))) {
                plz = replaceUnderscores(plz);
            }  else { plz = null; }

            if (!(performanceType.equals("null"))) {
                performanceType = replaceUnderscores(performanceType);
                type = null;

                if (performanceType.equals("Movie") || performanceType.equals("Film")) {
                    type = type.MOVIE;
                } else if (performanceType.equals("Concert") || performanceType.equals("Konzert")) {
                    type = type.CONCERT;
                } else if (performanceType.equals("Musical") || performanceType.equals("Musical")) {
                    type = type.MUSICAL;
                } else if (performanceType.equals("Theater") || performanceType.equals("Theater")) {
                    type = type.THEATER;
                } else if (performanceType.equals("Oper") || performanceType.equals("Opera")) {
                    type = type.OPER;
                }
            }

            if (!(price.equals("null"))) {
                price = replaceUnderscores(price);
                p_price = Integer.parseInt(price);
            }  else { p_price = 0; }

            if (!(artist.equals("null"))) {
                artist = replaceUnderscores(artist);
                if (artist.equals("")) {
                    artist = "null";
                }
            } else {
                artist = null;}

            resultList = this.performanceDao.filter(performanceName, town, plz, type, p_price, artist);

            if (!(date.equals("null"))) {
                date = replaceUnderscores(date);
                p_date = date;
            }

            if (!(time.equals("null"))) {
                time = replaceUnderscores(time);
                time += ":00.0";
                p_time = time;
            }

            resultList = filterAfterDateTime(resultList, p_date, p_time);
            return resultList;

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * @author Sissi Zhan 1325880
     *
     * gets all performances from a specified date or date+time
     */
    private List<Performance> filterAfterDateTime(List<Performance> list, String date, String time) throws ServiceException {
        try {
            List<Performance> returnList = new ArrayList<>();
            int startIndex = 0;
            int endIndex = 0;
            String dateTime = "";
            boolean dateIsNull = true;

            if (date == null && time == null) {
                return list;
            }
            if (date != null) {
                endIndex = 10;
                dateTime += date;
                dateIsNull = false;
            }
            if (time != null) {
                endIndex = 21;
                if (!dateIsNull) {
                    dateTime += " ";
                } else {
                    startIndex = 11;
                }
                dateTime += time;
            }

            Iterator it = list.iterator();
            while (it.hasNext()) {
                Performance p = (Performance) it.next();
                List<Timestamp> dates = this.performanceDao.getDatesOfPerformance(p.getId());
                for (Timestamp timeStamp : dates) {
                    String sqlDate = timeStamp.toString();
                    if (sqlDate.substring(startIndex, endIndex).equals(dateTime)) {
                        returnList.add(p);
                        break;
                    }
                }
            }
            return returnList;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * @author Sissi Zhan 1325580
     */
    private String replaceUnderscores(String word) {
        return word.replaceAll("_", " ");
    }

    private void setPerformanceDao(PerformanceDao dao) {
        this.performanceDao = dao;
    }

}
