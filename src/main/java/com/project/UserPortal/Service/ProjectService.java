package com.project.UserPortal.Service;

import com.google.gson.Gson;
import com.project.UserPortal.Domain.Department;
import com.project.UserPortal.Domain.Employee;
import com.project.UserPortal.Domain.Project;
import com.project.UserPortal.Exceptions.CustomException;
import com.project.UserPortal.Exceptions.ResourceAlreadyExists;
import com.project.UserPortal.Exceptions.ResourceNotFoundException;
import com.project.UserPortal.Repository.DepartmentRepository;
import com.project.UserPortal.Repository.EmployeeRepository;
import com.project.UserPortal.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProjectService
{
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

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
    public Project checkIfprojectIsValid(int id)
    {
        Optional<Project> optionalProject=projectRepository.findById(id);
        if(!optionalProject.isPresent()) throw new ResourceNotFoundException("Project id doesn't exist!!");
        return optionalProject.get();
    }
    public Project mapEmployees(int id,Project project)
    {
        Project project1=checkIfprojectIsValid(id);
        Optional<Department> department=departmentRepository.findById(project1.getDepartment().getId());
        Department department1=department.get();
        Set<Employee> employeeExist=checkIfEmployeeAlreadyExist(project.getEmployees(),project1.getEmployees());
        Set<Employee> employeeNotExist=checkEmployeeAreRegisteredOrNot(project.getEmployees());
        if(employeeExist.size()>0 || employeeNotExist.size()>0)
        {
            Map<String,Set<Employee>> map=new HashMap<String,Set<Employee>>();
            if(employeeExist.size()>0)
                map.put("Employee Already Exist!!",employeeExist);
            if(employeeNotExist.size()>0)
                map.put("Employee Is is not valid!!",employeeNotExist);
            Gson gson = new Gson();
            String jsonString = gson.toJson(map);
            throw new CustomException(jsonString);
        }
        Set <Employee> employeeSet=checkEmployeeWithDepartmentId(project.getEmployees(),department1.getId());
        project1.getEmployees().addAll(employeeSet);
        for(Employee e:employeeSet)
            e.getProjects().add(project1);
        return projectRepository.save(project1);
    }
    public Project addNewEmployeeSet(int id, Project project)
    {
        Project project1=checkIfprojectIsValid(id);
        Optional<Department> department=departmentRepository.findById(project1.getDepartment().getId());
        Department department1=department.get();
        Set<Employee> employeeSet1 = project1.getEmployees();
        Set<Employee> employeeNotExist=checkEmployeeAreRegisteredOrNot(project.getEmployees());
        if(employeeNotExist.size()>0)
        {
            Gson gson = new Gson();
            String jsonString = gson.toJson(employeeNotExist);
            throw new CustomException("Employees are not Exist :=> "+jsonString);
        }
        Set <Employee> employeeSet=checkEmployeeWithDepartmentId(project.getEmployees(),department1.getId());
        Project project2=removeExistingEmployee(project1);
        project2.getEmployees().addAll(employeeSet);
        for(Employee e:employeeSet)
            e.getProjects().add(project2);
        return projectRepository.save(project2);

    }
    public Project untagEmployees(int id,Project project)
    {
        Project project1=checkIfprojectIsValid(id);
        Set<Employee> employeeSet=project1.getEmployees();
        for(Employee e:project.getEmployees())
        {
            Employee h=employeeSet.stream()
                    .filter(e1-> (e.getId()==e.getId()))
                    .findAny().orElse(null);
            if(h!=null)
            {
                Employee e1 = employeeRepository.findById(e.getId()).get();
                e1.getProjects().remove(project1);
                employeeSet.remove(e1);
            }
        }
        project1.getEmployees().clear();
        project1.getEmployees().addAll(employeeSet);
        return projectRepository.save(project1);

    }
    public Set<Employee> checkIfEmployeeAlreadyExist(Set<Employee> employees,Set<Employee> employeeSet)
    {
        Set <Employee> employeeSet1=new HashSet<>();
        for(Employee e : employees)
        {
            Employee james = employeeSet.stream()
                    .filter(e1 -> (e.getId()==e1.getId()))
                    .findAny()
                    .orElse(null);
            if(james!=null)
                employeeSet1.add(e);
        }
        return employeeSet1;
    }
    public Set<Employee> checkEmployeeAreRegisteredOrNot(Set<Employee> employees)
    {
        Set<Employee> employeeSet=new HashSet<>();
        for(Employee e : employees) {
            Optional<Employee> e1 = employeeRepository.findById(e.getId());
            if (!e1.isPresent())
                employeeSet.add(e);
        }
        return employeeSet;
    }
    public Set<Employee>  checkEmployeeWithDepartmentId(Set<Employee> employees, int deptId)
    {
        Set<Employee> employeeSet=new HashSet<>();
        for(Employee e : employees)
        {
            Optional<Employee> e1=employeeRepository.findById(e.getId());
            Employee employee=e1.get();
            int i=employee.getDepartment().getId();
            if(employee.getDepartment().getId()!=deptId)
            {
                Gson gson=new Gson();
                String jsonString=gson.toJson(e);
                throw new CustomException("Project and employee dept is not same!! "+jsonString);
            }
            employeeSet.add(e1.get());
        }
        return employeeSet;

    }
    public Project removeExistingEmployee(Project project)
    {
        Set<Employee> employeeSet=project.getEmployees();
        for(Employee e:employeeSet) {
            e.getProjects().remove(project);
           // e.getProjects();
        }
        project.getEmployees().clear();
        return project;
    }
}
