package com.project.UserPortal.Mapper;

import com.project.UserPortal.DTO.DepartmentDTO;
import com.project.UserPortal.Domain.Department;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface DepartmentMapper
{
    DepartmentMapper Instance= Mappers.getMapper(DepartmentMapper.class);

    DepartmentDTO convertDepartmentToDTO(Department department);
    Department convertDTOToDepartment(DepartmentDTO departmentDTO);
    Set<Department> map(Set<DepartmentDTO> departmentDTOSet);
    Set<DepartmentDTO> map1(Set<Department> department);
}
