package at.ac.tuwien.inso.ticketline.model;


import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Lina Wang, 1326922
 *
 * It saves all news, that are already read by employee
 */
@Entity
public class LocalStorage implements Serializable{

    private static final long serialVersionUID = 2151240223868898930L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer employeeId;

    @Column(nullable = true)
    private Integer newsId;

    public LocalStorage(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }
}
