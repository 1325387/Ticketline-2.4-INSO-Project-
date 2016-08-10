package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.*;
import java.util.Date;

/**
 * The credit card entity.
 */
@Entity
public class Creditcard extends MethodOfPayment {

    private static final long serialVersionUID = -2150182589389024931L;

    private String owner;

    @Column(nullable = false)
    private String creditcardNumber;

    @Temporal(TemporalType.DATE)
    private Date validThru;

    @Enumerated(EnumType.STRING)
    private CreditcardType creditcardType;

    /**
     * Instantiates a new creditcard.
     */
    public Creditcard() {
    }

    /**
     * Instantiates a new creditcard.
     *
     * @param id the id
     * @param creditcardNumber the creditcard number
     */
    public Creditcard(Integer id, String creditcardNumber) {
        super(id, null);
        this.creditcardNumber = creditcardNumber;
    }

    /**
     * Instantiates a new creditcard.
     *
     * @param id the id
     * @param deleted the deleted
     * @param creditcardNumber the creditcard number
     * @param owner the owner
     * @param validThru the valid thru
     * @param type the type
     */
    public Creditcard(Integer id, Boolean deleted, String creditcardNumber, String owner, Date validThru, CreditcardType type) {
        super(id, deleted);
        this.owner = owner;
        this.creditcardNumber = creditcardNumber;
        this.validThru = validThru;
        this.creditcardType = type;
    }

    /**
     * Gets the owner.
     *
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the owner.
     *
     * @param owner the new owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Gets the creditcard number.
     *
     * @return the creditcard number
     */
    public String getCreditcardNumber() {
        return creditcardNumber;
    }

    /**
     * Sets the creditcard number.
     *
     * @param creditcardNumber the new creditcard number
     */
    public void setCreditcardNumber(String creditcardNumber) {
        this.creditcardNumber = creditcardNumber;
    }

    /**
     * Gets the valid thru.
     *
     * @return the valid thru
     */
    public Date getValidThru() {
        return validThru;
    }

    /**
     * Sets the valid thru.
     *
     * @param validThru the new valid thru
     */
    public void setValidThru(Date validThru) {
        this.validThru = validThru;
    }

    /**
     * Gets the creditcard type.
     *
     * @return the creditcard type
     */
    public CreditcardType getCreditcardType() {
        return creditcardType;
    }

    /**
     * Sets the creditcard type.
     *
     * @param creditcardType the new creditcard type
     */
    public void setCreditcardType(CreditcardType creditcardType) {
        this.creditcardType = creditcardType;
    }

}
