package at.ac.tuwien.inso.ticketline.dto;

import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @author Lina Wang 1326922
 */
public class GalleryDto {

    @NotNull
    @ApiModelProperty(value = "ID of gallery")
    private int id;

    @ApiModelProperty(value = "name of gallery")
    private String name;

    @ApiModelProperty(value = "description of gallery")
    private String description;

    @NotNull
    @ApiModelProperty(value = "sequence of gallery")
    private Integer sequence;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}
