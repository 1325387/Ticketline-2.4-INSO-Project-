package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author Aysel Oeztuerk 0927011
 */
public class AddressDto {

    @NotNull
    @Size(min = 1, max = 50)
    @ApiModelProperty(value = "Address of customer", required = true)
    private String street;

    @NotNull
    @Size(min = 1, max = 50)
    @ApiModelProperty(value = "City of customer", required = true)
    private String city;

    @NotNull
    @Size(min = 1, max = 25)
    @ApiModelProperty(value = "Postal code of customer", required = true)
    private String postalCode;

    @NotNull
    @Size(min = 1, max = 50)
    @ApiModelProperty(value = "Country of customer", required = true)
    private String country;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

