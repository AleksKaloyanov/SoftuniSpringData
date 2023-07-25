package com.example.automappingobjects.services;

import com.example.automappingobjects.model.dto.ManagerDto;

import java.util.List;

public interface EmployeeService {
    ManagerDto findOne(Long id);

    List<ManagerDto> findAll();
}
