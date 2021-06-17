package com.project.UserPortal.Service;

import com.project.UserPortal.Domain.Department;
import com.project.UserPortal.Domain.Project;
import com.project.UserPortal.Repository.DepartmentRepository;
import com.project.UserPortal.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService
{
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public Project addProject(Project project)
    {
        Optional<Department> department=departmentRepository.findByName(project.getDepartment().getName());
        if(!department.isPresent())
            return null;
        project.setDepartment(department.get());
        return projectRepository.save(project);
    }
    public boolean deleteProject(int id)
    {
        Optional<Project> optionalProject=projectRepository.findById(id);
        if(optionalProject.isPresent())
        {
            projectRepository.deleteById(id);
            return true;
        }
        return  false;
    }
    public Project updateProject(int id,Project project)
    {
        Optional<Department> optionalDepartment=departmentRepository.findById(project.getDepartment().getId());
        if(!optionalDepartment.isPresent())
            return null;
        Optional<Project> optionalProject=projectRepository.findById(id);
        if(!optionalProject.isPresent())
        return null;
        project.setId(optionalProject.get().getId());
        project.setDepartment(optionalDepartment.get());
        return projectRepository.save(project);

    }
    public Project getProject(int id)
    {
        Optional<Project> optionalProject=projectRepository.findById(id);
        if(optionalProject.isPresent())
            return optionalProject.get();
        return null;
    }
    public List<Project> getALlProjects()
    {
        List<Project> projects=new ArrayList<>();
        projectRepository.findAll().forEach(projects:: add);
        return projects;
    }
}
