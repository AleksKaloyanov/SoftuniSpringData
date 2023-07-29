package softuni.exam.models.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "astronomer")
@XmlAccessorType(XmlAccessType.FIELD)
public class AstronomerSeedDto {
    @NotNull
    @XmlElement(name = "average_observation_hours")
    @DecimalMin(value = "500.00")
    private Double averageObservationHours;

    @XmlElement
    private String  birthday;

    @NotNull
    @XmlElement(name = "first_name")
    @Size(min = 2, max = 30)
    private String firstName;

    @NotNull
    @XmlElement(name = "last_name")
    @Size(min = 2, max = 30)
    private String lastName;

    @NotNull
    @XmlElement
    @DecimalMin(value = "15000.00")
    private Double salary;

    @NotNull
    @XmlElement(name = "observing_star_id")
    private Long observingStarId;

    public AstronomerSeedDto() {
    }

    public Double getAverageObservationHours() {
        return averageObservationHours;
    }

    public void setAverageObservationHours(Double averageObservationHours) {
        this.averageObservationHours = averageObservationHours;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Long getObservingStarId() {
        return observingStarId;
    }

    public void setObservingStarId(Long observingStarId) {
        this.observingStarId = observingStarId;
    }
}
