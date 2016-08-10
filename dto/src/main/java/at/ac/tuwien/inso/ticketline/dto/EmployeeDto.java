package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @author Jayson Asenjo 1325387
 */
public class EmployeeDto {

    @ApiModelProperty(value = "Id of employee")
    private int id;
    @NotNull
    @ApiModelProperty(value = "First name of employee")
    private String firstname;
    @NotNull
    @ApiModelProperty(value = "Last name of employee")
    private String lastname;
    @NotNull
    @ApiModelProperty(value = "Username of employee")
    private String username;

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
