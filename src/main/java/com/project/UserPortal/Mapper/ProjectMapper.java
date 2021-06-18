package com.project.UserPortal.Mapper;

import com.project.UserPortal.DTO.ProjectDTO;
import com.project.UserPortal.Domain.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface ProjectMapper
{
    ProjectMapper Instance= Mappers.getMapper(ProjectMapper.class);

    @Mapping(target="deptId", source="department.id")
    ProjectDTO map(Project project);

    @Mapping(target="department.id", source="projectDTO.deptId")
    Project map(ProjectDTO projectDTO);

    Set<Project> convertDTO(Set<ProjectDTO> projectDTO);
    Set<ProjectDTO> convert(Set<Project> projects);
}
