package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.PerformanceType;
import at.ac.tuwien.inso.ticketline.model.Topten;
import at.ac.tuwien.inso.ticketline.model.ToptenPerformances;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of additional methods for {@link at.ac.tuwien.inso.ticketline.model.Show}
 *
 * Created by Aysel Oeztuerk 0927011.
 */
public class ShowDaoImpl implements ShowDaoCustom {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Topten> getTop10ShowsOfPerformanceType(int year, int month, PerformanceType performanceType) {

        LocalDate currentDate = LocalDate.of(year, month, 1);
        Date dateLimit = java.sql.Date.valueOf(currentDate);

        String queryString = "SELECT NEW Topten(sh.id, COUNT(sh.id), p.name, p.description, sh.dateOfPerformance) " +
                "FROM Ticket t " +
                "INNER JOIN t.ticketIdentifiers ti " +
                "INNER JOIN t.show sh " +
                "INNER JOIN sh.performance p " +
                "INNER JOIN ti.receiptEntry re " +
                "INNER JOIN re.receipt r " +
                "WHERE ti.valid = true AND p.performanceType = :performanceType " +
                "AND MONTH(r.transactionDate) = MONTH(" + "'" + dateLimit + "'" +") " +
                "AND YEAR(r.transactionDate) = YEAR(" + "'" + dateLimit + "'" +") " +
                "GROUP BY sh.id " +
                "ORDER BY COUNT(sh.id) DESC, " +
                "sh.dateOfPerformance DESC ";

        TypedQuery<Topten> query = entityManager.createQuery(queryString, Topten.class);
        query.setParameter("performanceType", performanceType);
        query.setMaxResults(10);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ToptenPerformances> getTop10PerformancesOfPerformanceType(int year, int month, PerformanceType performanceType) {

        LocalDate currentDate = LocalDate.of(year, month, 1);
        Date dateLimit = java.sql.Date.valueOf(currentDate);

        String queryString = "SELECT NEW ToptenPerformances(p.id, COUNT(p.id), p.name, p.description) " +
                "FROM Ticket t " +
                "INNER JOIN t.ticketIdentifiers ti " +
                "INNER JOIN t.show sh " +
                "INNER JOIN sh.performance p " +
                "INNER JOIN ti.receiptEntry re " +
                "INNER JOIN re.receipt r " +
                "WHERE ti.valid = true AND p.performanceType = :performanceType " +
                "AND MONTH(r.transactionDate) = MONTH(" + "'" + dateLimit + "'" +") " +
                "AND YEAR(r.transactionDate) = YEAR(" + "'" + dateLimit + "'" +") " +
                "GROUP BY p.id " +
                "ORDER BY COUNT(p.id) DESC ";

        TypedQuery<ToptenPerformances> query = entityManager.createQuery(queryString, ToptenPerformances.class);
        query.setParameter("performanceType", performanceType);
        query.setMaxResults(10);

        return query.getResultList();
    }
}

