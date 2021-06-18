package com.project.UserPortal.Service;

import com.project.UserPortal.Domain.Department;
import com.project.UserPortal.Domain.Project;
import com.project.UserPortal.Exceptions.ResourceAlreadyExists;
import com.project.UserPortal.Exceptions.ResourceNotFoundException;
import com.project.UserPortal.Repository.DepartmentRepository;
import com.project.UserPortal.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectService
{
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    public Project addProject(Project project)
    {
        Optional<Department> department=departmentRepository.findById(project.getDepartment().getId());
        if(!department.isPresent()) throw new ResourceNotFoundException("Department Id doesn't exist!!");
        Optional<Project> project1=projectRepository.findByName(project.getName());
        if(project1.isPresent()) throw new ResourceAlreadyExists("Project Already Exist with Name "+project.getName());
        project.setDepartment(department.get());
        return projectRepository.save(project);
    }
    public Project updateProject(int id,Project project)
    {
        Optional<Project> optionalProject=projectRepository.findById(id);
        if(!optionalProject.isPresent()) throw new ResourceNotFoundException("Project id doesn't exist!!");
        Optional<Department> optionalDepartment=departmentRepository.findById(project.getDepartment().getId());
        if(!optionalDepartment.isPresent()) throw new ResourceNotFoundException("Department id is not valid");
        project.setId(id);
        project.setDepartment(optionalDepartment.get());
        return projectRepository.save(project);
    }
    public void deleteProject(int id)
    {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (!optionalProject.isPresent()) throw new ResourceNotFoundException("Project id doesn't exist!!");
        projectRepository.deleteById(id);
    }

    public Project getProject(int id)
    {
        Optional<Project> optionalProject=projectRepository.findById(id);
        if(!optionalProject.isPresent()) throw new ResourceNotFoundException("Project id doesn't exist!!");
        return optionalProject.get();
    }
    public Set<Project> getALlProjects(int pageno,int pagesize)
    {
        Pageable paging= PageRequest.of(pageno,pagesize);
        Page<Project> projects=projectRepository.findAll(paging);
        return projects.toSet();
    }
}
