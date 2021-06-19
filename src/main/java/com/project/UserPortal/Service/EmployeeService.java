package com.project.UserPortal.Service;

import com.project.UserPortal.Domain.Department;
import com.project.UserPortal.Domain.Employee;
import com.project.UserPortal.Domain.Project;
import com.project.UserPortal.Exceptions.ResourceNotFoundException;
import com.project.UserPortal.Repository.DepartmentRepository;
import com.project.UserPortal.Repository.EmployeeRepository;
import com.project.UserPortal.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
    public ResponseEntity<?> mapProject(int id,Set<Project> projects )
    {
        Optional<Employee> optionalEmployee=employeeRepository.findById(id);
        if(!optionalEmployee.isPresent())
            return new ResponseEntity<>("Employee doesn't exist!!",HttpStatus.NOT_FOUND);
        Employee employee=optionalEmployee.get();
        employee.setId(id);
        employee.getProjects().addAll(projects);
//        for(Project p : projects)
//        {
//            employee.addProject(p);
//        }
        return new ResponseEntity<>(employeeRepository.save(employee),HttpStatus.OK);

    }
}
