package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.*;

public class PartImportDto {
    @Expose
    private String partName;
    @Expose
    private Double price;
    @Expose
    private String quantity;

    public PartImportDto() {
    }

    @Size(min = 2, max = 19)
    @NotNull
    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    @DecimalMin(value = "10.0")
    @DecimalMax(value = "2000.0")
    @NotNull
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Positive
    @NotNull
    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
