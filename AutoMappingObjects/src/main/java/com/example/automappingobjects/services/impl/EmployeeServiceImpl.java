package com.example.automappingobjects.services.impl;

import com.example.automappingobjects.model.dto.ManagerDto;
import com.example.automappingobjects.repositories.EmployeeRepository;
import com.example.automappingobjects.services.EmployeeService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper mapper;


    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }


    @Override
    public ManagerDto findOne(Long id) {
        return mapper.map(
                this.employeeRepository.findById(id).orElseThrow(),
                ManagerDto.class
        );
    }

    @Override
    public List<ManagerDto> findAll() {
        return mapper.map(
                this.employeeRepository.findAll(),
                new TypeToken<List<ManagerDto>>() {
                }.getType()
        );
    }
}
