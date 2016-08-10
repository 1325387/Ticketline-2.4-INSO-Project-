package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by Sissi on 01.05.2016.
 * @author Aysel Oeztuerk 0927011
 */
public class CustomerDto {

    @NotNull
    @ApiModelProperty(value = "ID of customer")
    private int id;

    @NotNull
    @Size(min = 1, max = 50)
    @ApiModelProperty(value = "Firstname of customer", required = true)
    private String firstname;

    @NotNull
    @Size(min = 1, max = 50)
    @ApiModelProperty(value = "Lastname of customer", required = true)
    private String lastname;

    private AddressDto address;

    @NotNull
    @ApiModelProperty(value = "Customers date of birth")
    private Date dateOfBirth;

    @Size(min = 0, max = 50)
    @ApiModelProperty(value = "Title of customer")
    private String title;

    @NotNull
    @Size(min = 1, max = 50)
    @ApiModelProperty(value = "Phonenumber of customer", required = true)
    private String phoneNumber;

    @Size(min = 0, max = 50)
    @ApiModelProperty(value = "Email of customer")
    private String email;

    @Size(min = 0, max = 50)
    @ApiModelProperty(value = "Gender of customer")
    private String gender;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title_tf) {
        this.title = title;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phonenumber) {
        this.phoneNumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}

