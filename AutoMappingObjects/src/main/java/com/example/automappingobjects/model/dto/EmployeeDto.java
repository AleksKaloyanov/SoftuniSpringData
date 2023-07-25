package com.example.automappingobjects.model.dto;

import java.math.BigDecimal;

public class EmployeeDto extends BasicEmployeeDto{

    private BigDecimal income;


    public void setIncome(BigDecimal income) {
        this.income = income;
    }
    public BigDecimal getIncome() {
        return income;
    }
}
