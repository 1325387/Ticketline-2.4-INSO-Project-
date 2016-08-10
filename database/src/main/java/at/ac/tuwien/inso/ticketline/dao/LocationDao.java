package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DAO for {@link at.ac.tuwien.inso.ticketline.model.Location}
 *
 * Created by Aysel on 02.05.2016.
 */
@Repository
public interface LocationDao extends JpaRepository<Location, Integer> {

    /**
     * @author Sissi Zhan 1325880
     * @return all cities in database
     */
    @Query(value = "SELECT l.address.city " +
            "FROM Location AS l " +
            "GROUP BY l.address.city " +
            "ORDER BY l.address.city asc " )
    public List<String> getCities();

    /**
     * @author Sissi Zhan 1325880
     * @return all postalcodes in database
     */
    @Query(value = "SELECT l.address.postalCode " +
            "FROM Location As l " +
            "GROUP BY l.address.postalCode " +
            "ORDER BY l.address.postalCode asc ")
    public List<String> getPostalCodes();
}
