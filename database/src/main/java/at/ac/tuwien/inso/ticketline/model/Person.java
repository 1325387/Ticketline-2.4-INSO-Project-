/**
 *
 */
package at.ac.tuwien.inso.ticketline.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The person entity.
 */
@MappedSuperclass
public abstract class Person implements Serializable {

    private static final long serialVersionUID = -1102929654096272086L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = true)
    private String title;

    private Address address;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = true)
    private Gender gender;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    private String email;

    private String phoneNumber;

    private String username;

    private String passwordHash;

    /**
     * Instantiates a new person.
     */
    public Person() {
    }

    /**
     * Instantiates a new person.
     *
     * @param id the id
     * @param gender the gender
     * @param title the title
     * @param firstname the firstname
     * @param lastname the lastname
     * @param email the email
     * @param phoneNumber the phone number
     * @param dateOfBirth the date of birth
     */
    public Person(Integer id, Gender gender, String title, String firstname, String lastname, String email,
                  String phoneNumber, Date dateOfBirth) {
        this.id = id;
        this.gender = gender;
        this.title = title;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Instantiates a new person.
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
     */
    public Person(Integer id, Gender gender, String title, String firstname, String lastname, String email,
                  String phoneNumber, Date dateOfBirth, Address address) {
        this.id = id;
        this.gender = gender;
        this.title = title;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
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
     * Gets the firstname.
     *
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the firstname.
     *
     * @param firstname the new firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Gets the lastname.
     *
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the lastname.
     *
     * @param lastname the new lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the address.
     *
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the address.
     *
     * @param address the new address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Gets the gender.
     *
     * @return the gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets the gender.
     *
     * @param gender the new gender
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Gets the date of birth.
     *
     * @return the date of birth
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth.
     *
     * @param dateOfBirth the new date of birth
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number.
     *
     * @param phoneNumber the new phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password hash.
     *
     * @return the password hash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets the password hash.
     *
     * @param passwordHash the new password hash
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

}
