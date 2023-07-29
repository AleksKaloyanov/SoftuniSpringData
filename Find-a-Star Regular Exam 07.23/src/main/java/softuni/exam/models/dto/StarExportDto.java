package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;

import java.util.Locale;

public class StarExportDto {
    @Expose
    private String name;
    @Expose
    private Double lightYears;
    @Expose
    private String description;
    @Expose
    private String constellationName;

    public StarExportDto() {
    }

    public StarExportDto(String name, Double lightYears, String description, String constellationName) {
        this.name = name;
        this.lightYears = lightYears;
        this.description = description;
        this.constellationName = constellationName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLightYears() {
        return lightYears;
    }

    public void setLightYears(Double lightYears) {
        this.lightYears = lightYears;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConstellationName() {
        return constellationName;
    }

    public void setConstellationName(String constellationName) {
        this.constellationName = constellationName;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "Star: %s%n" +
                        "   *Distance: %.2f light years%n" +
                        "   **Description: %s%n" +
                        "   ***Constellation: %s",
                this.getName(),
                this.getLightYears(),
                this.getDescription(),
                this.getConstellationName()
        );
    }
}
