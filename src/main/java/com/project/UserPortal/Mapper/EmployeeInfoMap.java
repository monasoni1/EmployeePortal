package com.project.UserPortal.Mapper;

import com.project.UserPortal.DTO.EmployeeDTO;
import com.project.UserPortal.DTO.EmployeeInfoDTO;
import com.project.UserPortal.Domain.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeInfoMap
{
    EmployeeInfoMap Instance= Mappers.getMapper(EmployeeInfoMap.class);
    EmployeeInfoDTO map(Employee employee);
    Employee map(EmployeeInfoDTO employeeInfoDTO);
}
