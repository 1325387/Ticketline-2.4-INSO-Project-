package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The customer entity.
 */
@Entity
public class Customer extends Person {

    private static final long serialVersionUID = 4192214529072911981L;

    @Enumerated(EnumType.STRING)
    private CustomerStatus customerStatus;

    @Enumerated(EnumType.STRING)
    private CustomerGroup customerGroup;

    private String ticketcardNumber;

    @Temporal(TemporalType.DATE)
    private Date ticketcardValidThru;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", fetch = FetchType.EAGER)
    private List<MethodOfPayment> methodOfPayments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Receipt> receipts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Reservation> reservations;

    /**
     * Instantiates a new customer.
     */
    public Customer() {
    }

    /**
     * Instantiates a new customer.
     *
     * @param id the id
     * @param gender the gender
     * @param title the title
     * @param firstname the firstname
     * @param lastname the lastname
     * @param email the email
     * @param phoneNumber the phone number
     * @param dateOfBirth the date of birth
     * @param customerStatus the customer status
     * @param customerGroup the customer group
     * @param ticketcardNumber the ticketcard number
     * @param ticketcardValidThru the ticketcard valid thru
     */
    public Customer(Integer id, Gender gender, String title, String firstname, String lastname, String email,
                    String phoneNumber, Date dateOfBirth, CustomerStatus customerStatus, CustomerGroup customerGroup,
                    String ticketcardNumber, Date ticketcardValidThru) {
        super(id, gender, title, firstname, lastname, email, phoneNumber, dateOfBirth);
        this.customerStatus = customerStatus;
        this.customerGroup = customerGroup;
        this.ticketcardNumber = ticketcardNumber;
        this.ticketcardValidThru = ticketcardValidThru;
    }

    /**
     * Instantiates a new customer.
     *
     * @param id the id
     * @param gender the gender
     * @param title the title
     * @param firstname the firstname
     * @param lastname the lastname
     * @param email the email
     * @param phoneNumber the phone number
     * @param dateOfBirth the date of birth
     * @param address the address
     * @param customerStatus the customer status
     * @param customerGroup the customer group
     * @param ticketcardNumber the ticketcard number
     * @param ticketcardValidThru the ticketcard valid thru
     */
    public Customer(Integer id, Gender gender, String title, String firstname, String lastname, String email,
                    String phoneNumber, Date dateOfBirth, Address address, CustomerStatus customerStatus, CustomerGroup customerGroup,
                    String ticketcardNumber, Date ticketcardValidThru) {
        super(id, gender, title, firstname, lastname, email, phoneNumber, dateOfBirth, address);
        this.customerStatus = customerStatus;
        this.customerGroup = customerGroup;
        this.ticketcardNumber = ticketcardNumber;
        this.ticketcardValidThru = ticketcardValidThru;
    }

    /**
     * Gets the customer status.
     *
     * @return the customer status
     */
    public CustomerStatus getCustomerStatus() {
		return customerStatus;
	}

	/**
	 * Sets the customer status.
	 *
	 * @param customerStatus the new customer status
	 */
	public void setCustomerStatus(CustomerStatus customerStatus) {
		this.customerStatus = customerStatus;
	}

	/**
     * Gets the customer group.
     *
     * @return the customer group
     */
    public CustomerGroup getCustomerGroup() {
        return customerGroup;
    }

    /**
     * Sets the customer group.
     *
     * @param customerGroup the new customer group
     */
    public void setCustomerGroup(CustomerGroup customerGroup) {
        this.customerGroup = customerGroup;
    }

    /**
     * Gets the ticketcard number.
     *
     * @return the ticketcard number
     */
    public String getTicketcardNumber() {
        return ticketcardNumber;
    }

    /**
     * Sets the ticketcard number.
     *
     * @param ticketcardNumber the new ticketcard number
     */
    public void setTicketcardNumber(String ticketcardNumber) {
        this.ticketcardNumber = ticketcardNumber;
    }

    /**
     * Gets the ticketcard valid thru.
     *
     * @return the ticketcard valid thru
     */
    public Date getTicketcardValidThru() {
        return ticketcardValidThru;
    }

    /**
     * Sets the ticketcard valid thru.
     *
     * @param ticketcardValidThru the new ticketcard valid thru
     */
    public void setTicketcardValidThru(Date ticketcardValidThru) {
        this.ticketcardValidThru = ticketcardValidThru;
    }

    /**
     * Gets the method of payments.
     *
     * @return the method of payments
     */
    public List<MethodOfPayment> getMethodOfPayments() {
        return methodOfPayments;
    }

    /**
     * Sets the method of payments.
     *
     * @param methodOfPayments the new method of payments
     */
    public void setMethodOfPayments(List<MethodOfPayment> methodOfPayments) {
        this.methodOfPayments = methodOfPayments;
    }

    /**
     * Gets the receipts.
     *
     * @return the receipts
     */
    public List<Receipt> getReceipts() {
        return receipts;
    }

    /**
     * Sets the receipts.
     *
     * @param receipts the new receipts
     */
    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }

    /**
     * Gets the reservations.
     *
     * @return the reservations
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Sets the reservations.
     *
     * @param reservations the new reservations
     */
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * @author Aysel Öztürk 0927011
     */
    @Override
    public boolean equals(Object o) {
        Customer customer = (Customer) o;
        return customer.getId() == this.getId();
    }
}
