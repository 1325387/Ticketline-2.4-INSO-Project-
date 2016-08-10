package at.ac.tuwien.inso.ticketline.dao;

import at.ac.tuwien.inso.ticketline.model.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sissi Zhan 1325880
 */
@Repository
public interface ParticipationDao extends JpaRepository<Participation, Integer> {

}
