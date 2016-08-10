package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.*;
import java.util.Date;

/**
 * The employee entity.
 */
@Entity
public class Employee extends Person {

    private static final long serialVersionUID = 1021211581003682919L;

    private String insuranceNumber;

    @Enumerated(EnumType.STRING)
    private Permission permission;

    @Temporal(TemporalType.DATE)
    private Date employeedSince;

    @Column(nullable = true)
    private Integer attempts;

    /**
     * Instantiates a new employee.
     */
    public Employee() {
    }

    /**
     * Instantiates a new employee.
     *
     * @param firstname the firstname
     * @param lastname the lastname
     * @param username the username
     * @param passwordHash the password hash
     */
    public Employee(String firstname, String lastname, String username, String passwordHash) {
        setFirstname(firstname);
        setLastname(lastname);
        setUsername(username);
        setPasswordHash(passwordHash);
    }

    /**
     * Gets the insurance number.
     *
     * @return the insurance number
     */
    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    /**
     * Sets the insurance number.
     *
     * @param insuranceNumber the new insurance number
     */
    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    /**
     * Gets the permission.
     *
     * @return the permission
     */
    public Permission getPermission() {
        return permission;
    }

    /**
     * Sets the permission.
     *
     * @param permission the new permission
     */
    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    /**
     * Gets the employeed since.
     *
     * @return the employeed since
     */
    public Date getEmployeedSince() {
        return employeedSince;
    }

    /**
     * Sets the employeed since.
     *
     * @param employeedSince the new employeed since
     */
    public void setEmployeedSince(Date employeedSince) {
        this.employeedSince = employeedSince;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }


}
