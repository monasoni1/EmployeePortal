package com.project.UserPortal.Service;

import com.project.UserPortal.Domain.Department;
import com.project.UserPortal.Domain.Employee;
import com.project.UserPortal.Domain.Project;
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

    public ResponseEntity<?> addEmployee(Employee employee)
    {
        Optional<Department> optionalDepartment=departmentRepository.findByName(employee.getDepartment().getName());
        if(!optionalDepartment.isPresent())
            return new ResponseEntity<>("Department name is not valid", HttpStatus.NOT_FOUND);
       employee.setDepartment(optionalDepartment.get());
       return new ResponseEntity<>(employeeRepository.save(employee),HttpStatus.CREATED);

    }
    public ResponseEntity<?> updateEmployee(Employee employee ,int id)
    {
        Optional<Employee> optionalEmployee=employeeRepository.findById(id);
        if(!optionalEmployee.isPresent())
            return new ResponseEntity<>("Employee doesn't exist!!",HttpStatus.NOT_FOUND);
        employee.setId(id);
        Set<Project> projects=optionalEmployee.get().getProjects();


        return new ResponseEntity<>(employeeRepository.save(employee),HttpStatus.OK);

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
