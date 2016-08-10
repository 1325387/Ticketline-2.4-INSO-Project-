package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sissi Zhan 1325880
 */
@Repository
public interface ArtistDao extends JpaRepository<Artist, Integer>{

}
