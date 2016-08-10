package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The method of payment entity.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MethodOfPayment implements Serializable {

    private static final long serialVersionUID = -7609929677091471708L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    /**
    @OneToMany(mappedBy = "methodOfPayment")
    private List<Receipt> receipts;
    */

    private Boolean deleted;

    /**
     * Instantiates a new method of payment.
     */
    protected MethodOfPayment() {
    }

    /**
     * Instantiates a new method of payment.
     *
     * @param id the id
     * @param deleted the deleted
     */
    protected MethodOfPayment(Integer id, Boolean deleted) {
        this.id = id;
        this.deleted = deleted;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the customer.
     *
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer.
     *
     * @param customer the new customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets the deleted.
     *
     * @return the deleted
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * Sets the deleted.
     *
     * @param deleted the new deleted
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

}
