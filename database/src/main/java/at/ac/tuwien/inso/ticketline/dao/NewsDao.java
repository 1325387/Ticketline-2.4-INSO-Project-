package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.LocalStorage;
import at.ac.tuwien.inso.ticketline.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO for {@link at.ac.tuwien.inso.ticketline.model.News}
 */
@Repository
public interface NewsDao extends JpaRepository<News, Integer> {

    /**
     * Finds all news ordered by the submission date.
     *
     * @return the list of news
     */
    @Query(value = "SELECT n FROM News n ORDER BY n.submittedOn ASC")
    public List<News> findAllOrderBySubmittedOnAsc();


}
