package com.project.UserPortal.Mapper;

import com.project.UserPortal.DTO.ProjectDTO;
import com.project.UserPortal.Domain.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjectMapper
{
    ProjectMapper Instance= Mappers.getMapper(ProjectMapper.class);

    @Mapping(target="deptId", source="department.id")
    ProjectDTO map(Project project);

    @Mapping(target="department.id", source="projectDTO.deptId")
    Project map(ProjectDTO projectDTO);
}
