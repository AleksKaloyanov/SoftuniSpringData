package com.example.automappingobjects;

import com.example.automappingobjects.model.dto.ManagerDto;
import com.example.automappingobjects.services.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Main implements CommandLineRunner {

    private final EmployeeService employeeService;

    public Main(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @Override
    public void run(String... args) throws Exception {
//        ManagerDto managerDto = this.employeeService.findOne(1L);

        List<ManagerDto> managers = this.employeeService.findAll();

        managers.forEach(managerDto -> {

            System.out.println(managerDto.getFirstName() + " " +
                    managerDto.getLastName() + " | Employees: " + managerDto.getSubordinates().size());
            managerDto.getSubordinates()
                    .forEach(employeeDto -> {
                        System.out.println("\t" + employeeDto.getFirstName() +
                                " " + employeeDto.getLastName() + " : " + employeeDto.getIncome());
                    });
        });

    }
}
