package at.ac.tuwien.inso.ticketline.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The reservation entity.
 */
@Entity
public class Reservation implements Serializable {

    private static final long serialVersionUID = 6362706103122264420L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reservation", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<TicketIdentifier> ticketIdentifiers;

    /**
     * Instantiates a new reservation.
     */
    public Reservation() {
    }

    /**
     * Instantiates a new reservation.
     *
     * @param id the id
     * @param customer the customer
     * @param employee the employee
     */
    public Reservation(Integer id, Customer customer, Employee employee) {
        this.id = id;
        this.customer = customer;
        this.employee = employee;
    }

    /**
     * Instantiates a new reservation.
     *
     * @param id the id
     * @param customer the customer
     * @param employee the employee
     * @param ticketIdentifiers the ticket identifiers
     */
    public Reservation(Integer id, Customer customer, Employee employee, List<TicketIdentifier> ticketIdentifiers) {
        this.id = id;
        this.customer = customer;
        this.employee = employee;
        this.ticketIdentifiers = ticketIdentifiers;
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
     * Gets the employee.
     *
     * @return the employee
     */
    public Employee getEmployee() {
		return employee;
	}

	/**
	 * Sets the employee.
	 *
	 * @param employee the new employee
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/**
     * Gets the ticket identifiers.
     *
     * @return the ticket identifiers
     */
    public List<TicketIdentifier> getTicketIdentifiers() {
        return ticketIdentifiers;
    }

    /**
     * Sets the ticket identifiers.
     *
     * @param ticketIdentifiers the new ticket identifiers
     */
    public void setTicketIdentifiers(List<TicketIdentifier> ticketIdentifiers) {
        this.ticketIdentifiers = ticketIdentifiers;
    }

}
