package com.project.UserPortal.Service;

import com.google.gson.Gson;
import com.project.UserPortal.DTO.EmployeeDTO;
import com.project.UserPortal.Domain.Department;
import com.project.UserPortal.Domain.Employee;
import com.project.UserPortal.Domain.Project;
import com.project.UserPortal.Exceptions.CustomException;
import com.project.UserPortal.Exceptions.ResourceNotFoundException;
import com.project.UserPortal.Repository.DepartmentRepository;
import com.project.UserPortal.Repository.EmployeeRepository;
import com.project.UserPortal.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeService
{

    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;
    private ProjectRepository projectRepository;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, ProjectRepository projectRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.projectRepository = projectRepository;
    }

    public Employee addEmployee(Employee employee)
    {
        Optional<Department> optionalDepartment=departmentRepository.findById(employee.getDepartment().getId());
        if(!optionalDepartment.isPresent()) throw new ResourceNotFoundException("Department is not valid");
       employee.setDepartment(optionalDepartment.get());
       return employeeRepository.save(employee);

    }
    public Employee updateEmployee(Employee employee ,int id)
    {
        Optional<Employee> optionalEmployee=employeeRepository.findById(id);
        if(!optionalEmployee.isPresent()) throw new ResourceNotFoundException("Employee doesn't exist with ID "+id);
        employee.setId(id);
        //Set<Project> projects=optionalEmployee.get().getProjects();
        return employeeRepository.save(employee);
    }
    public Employee checkEmployeeIdisValid(int id)
    {
        Optional<Employee> employeeOptional=employeeRepository.findById(id);
        if(!employeeOptional.isPresent()) throw new ResourceNotFoundException("Employee doesn't Exist with ID :"+id);
        return employeeOptional.get();
    }
    public Employee mapProject(int id, Employee employee)
    {
        Employee employee1=checkEmployeeIdisValid(id);
        Department department=employee1.getDepartment();
        Set<Project> ProjectNotValid=checkIfProjectsAreNotValid(employee.getProjects());
        Set<Project> ProjectAreAlready=checkifProjectsAreAlreadyMapped(employee.getProjects(),employee1.getProjects());
        if(ProjectAreAlready.size()>0 || ProjectNotValid.size()>0)
        {
            Map<String,Set<Project>> map=new HashMap<>();
            if(ProjectAreAlready.size()>0)
                map.put("Projects Are Already Exist",ProjectAreAlready);
            if(ProjectNotValid.size()>0)
                map.put("Projects are Not Valid",ProjectNotValid);
            Gson gson=new Gson();
            String jsonString= gson.toJson(map);
            throw new CustomException(jsonString);
        }
        Set<Project> projectSet=checkProjectandEmployeedeptId(employee.getProjects(),department.getId());
        employee1.getProjects().addAll(projectSet);
        for(Project p :projectSet)
            p.getEmployees().add(employee1);
        return employeeRepository.save(employee1);
    }
    public Employee addEmployeeSet(int id,Employee employee)
    {
        Employee employee1=checkEmployeeIdisValid(id);
        Department department=employee1.getDepartment();
        Set<Project> ProjectNotValid=checkIfProjectsAreNotValid(employee.getProjects());
        if(ProjectNotValid.size()>0)
        {
            Gson gson=new Gson();
            String jsonString= gson.toJson("Projects id is not valid:"+ProjectNotValid);
            throw new ResourceNotFoundException(jsonString);
        }
        Set<Project> projectSet=checkProjectandEmployeedeptId(employee.getProjects(),department.getId());
        Employee employee2=RemoveExisting(employee1);
        employee2.setProjects(projectSet);
        for(Project p :projectSet)
            p.getEmployees().add(employee2);
        return employeeRepository.save(employee2);
    }
    public Employee RemoveExisting(Employee employee)
    {
        for(Project p : employee.getProjects())
            p.getEmployees().remove(employee);
        employee.getProjects().clear();
        return employee;
    }
    public Set<Project> checkProjectandEmployeedeptId(Set<Project> project,int deptId)
    {
        HashSet<Project> projects=new HashSet<>();
        for(Project p : project)
        {
            Project p1=projectRepository.findById(p.getId()).get();
            if(p1.getDepartment().getId()!=deptId)
            {
                Gson gson=new Gson();
                String jsonString=gson.toJson(p);
                throw new CustomException("Project and Employee Department is not Same "+jsonString);
            }
            projects.add(p1);
        }
        return projects;
    }
    public Set<Project> checkIfProjectsAreNotValid(Set<Project> projects)
    {
        HashSet<Project> projects1=new HashSet<>();
        for(Project p : projects)
        {
            Optional<Project> p1=projectRepository.findById(p.getId());
            if(!p1.isPresent())
                projects1.add(p);
        }
        return projects1;
    }
    public Set<Project> checkifProjectsAreAlreadyMapped(Set<Project> projects,Set<Project> projects1)
    {
        HashSet<Project> resSet=new HashSet<>();
        for(Project p : projects)
        {
            Project res=projects1.stream()
                    .filter(p1->(p.getId()==p.getId()))
                    .findAny().orElse(null);

            if(res!=null)
                resSet.add(p);

        }
        return resSet;
    }

}
