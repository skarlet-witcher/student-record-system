package cc.orangejuice.srs.programme.service.dto;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import cc.orangejuice.srs.programme.domain.enumeration.ProgrammePropType;

/**
 * A DTO for the ProgrammePropDict entity.
 */
public class ProgrammePropDictDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer forEnrollYear;

    @NotNull
    private ProgrammePropType type;

    private Integer forYearNo;

    private Integer forSemesterNo;

    /**
     * semester->factor, semester->belong_to_part, general->how_many_parts
     */
    @NotNull
    @ApiModelProperty(value = "semester->factor, semester->belong_to_part, general->how_many_parts", required = true)
    private String key;

    @NotNull
    private String value;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getForEnrollYear() {
        return forEnrollYear;
    }

    public void setForEnrollYear(Integer forEnrollYear) {
        this.forEnrollYear = forEnrollYear;
    }

    public ProgrammePropType getType() {
        return type;
    }

    public void setType(ProgrammePropType type) {
        this.type = type;
    }

    public Integer getForYearNo() {
        return forYearNo;
    }

    public void setForYearNo(Integer forYearNo) {
        this.forYearNo = forYearNo;
    }

    public Integer getForSemesterNo() {
        return forSemesterNo;
    }

    public void setForSemesterNo(Integer forSemesterNo) {
        this.forSemesterNo = forSemesterNo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProgrammePropDictDTO programmePropDictDTO = (ProgrammePropDictDTO) o;
        if (programmePropDictDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), programmePropDictDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProgrammePropDictDTO{" +
            "id=" + getId() +
            ", forEnrollYear=" + getForEnrollYear() +
            ", type='" + getType() + "'" +
            ", forYearNo=" + getForYearNo() +
            ", forSemesterNo=" + getForSemesterNo() +
            ", key='" + getKey() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
