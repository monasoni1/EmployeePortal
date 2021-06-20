package com.project.UserPortal.Mapper;

import com.project.UserPortal.DTO.EmployeeDTO;
import com.project.UserPortal.DTO.EmployeeInfoDTO;
import com.project.UserPortal.DTO.ProjectDTO;
import com.project.UserPortal.Domain.Employee;
import com.project.UserPortal.Domain.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.Set;


@Mapper
public interface EmployeeMapper
{
    EmployeeMapper Instance= Mappers.getMapper(EmployeeMapper.class);

    @Mapping(target="deptId", source="department.id")
    EmployeeDTO convertEmpTODTO(Employee employee);

    @Mapping(target="department.id", source="employeeDTO.deptId")
    Employee convertDTOTOEMP(EmployeeDTO employeeDTO);

    Set<Employee> convertDTO(Set<EmployeeDTO> employeeDTOS);
    Set<EmployeeDTO> convert(Set<Employee> employees);

    Employee convertInfo(EmployeeInfoDTO employeeInfoDTO);
    EmployeeInfoDTO convertToInfo(Employee employee);
}
