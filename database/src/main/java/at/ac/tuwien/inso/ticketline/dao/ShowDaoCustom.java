package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.PerformanceType;
import at.ac.tuwien.inso.ticketline.model.Topten;
import at.ac.tuwien.inso.ticketline.model.ToptenPerformances;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Defines additional methods for {@link at.ac.tuwien.inso.ticketline.model.Show}
 * <p>
 * Created by Aysel Oeztuerk 0927011.
 */
@Repository
public interface ShowDaoCustom {


    /**
     * Returns the top ten shows of given type/category by count of sold tickets in one month.
     * If several shows have the same count of sold tickets, than they are ordered by dateOfPerformance.
     * The dates, which are closest to the current date come first.
     *
     * @param performanceType
     * @return a list of top 10 shows
     */
    List<Topten> getTop10ShowsOfPerformanceType(int year, int month, PerformanceType performanceType);

    /**
     * Returns the top ten perfromances of given type/category, year and month by count of sold tickets.
     *
     * @param performanceType type of the performance
     * @param year of the performance
     * @param month of the performance
     * @return a list of top 10 performances
     */
    List<ToptenPerformances> getTop10PerformancesOfPerformanceType(int year, int month, PerformanceType performanceType);

}