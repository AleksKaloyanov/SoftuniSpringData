package com.example.automappingobjects.model.dto;

public abstract class BasicEmployeeDto {
    private String firstName;
    private String lastName;
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
