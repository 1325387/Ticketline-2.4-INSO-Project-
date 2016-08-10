package at.ac.tuwien.inso.ticketline.dto;


import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Time;

/**
 * @author Sissi Zhan 1325880
 *
 * this class represents an object für filtering performances. It has the factors, which the performances should be
 * filtered after.
 *
 */
@ApiModel(value = "PerformanceFilterDto", description = "Data transfer object for an object with performance filtering attributes")
public class PerformanceFilterDto {

    //@Size(min = 1, max = 50)
    @ApiModelProperty(value = "Name of performance")
    String performanceName;

    @ApiModelProperty(value = "Date of show")
    java.sql.Date date;

    //@Size(min = 1, max = 50)
    @ApiModelProperty(value = "Town of show")
    String town;

    //@Size(min = 1, max = 25)
    @ApiModelProperty(value = "Postalcode of show")
    String plz;

    @ApiModelProperty(value = "Time of show")
    java.sql.Time time;

    @ApiModelProperty(value = "Type of performance")
    String performanceType;

    @ApiModelProperty(value = "Lowest price of show")
    Double price;

    @ApiModelProperty(value = "Artist of performance")
    String artist;

    public String getPerformanceName() {
        return performanceName;
    }

    public void setPerformanceName(String performanceName) {
        this.performanceName = performanceName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getPerformanceType() {
        return performanceType;
    }

    public void setPerformanceType(String performanceType) {
        this.performanceType = performanceType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
