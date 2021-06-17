package com.project.UserPortal.Controller;

import com.project.UserPortal.DTO.EmployeeDTO;
import com.project.UserPortal.Domain.Employee;
import com.project.UserPortal.Domain.Project;
import com.project.UserPortal.Mapper.EmployeeMapper;
import com.project.UserPortal.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/Employee")
public class EmployeeController
{

    private EmployeeService  employeeService;
    private EmployeeMapper employeeMapper;

    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @PostMapping(path="/")
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeDTO employeeDTO)
    {
        return employeeService.addEmployee(employeeMapper.convertDTOTOEMP(employeeDTO));
    }

    @PutMapping(path="/{id}")
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeDTO employeeDTO,@PathVariable int id)
    {
        return employeeService.updateEmployee(employeeMapper.convertDTOTOEMP(employeeDTO),id);
    }

    @PostMapping(path="/{id}/projects")
    public  ResponseEntity<?> mapProjects(@RequestBody Set<Project> projectList, @PathVariable int id)
    {
        return employeeService.mapProject(id,projectList);
    }
}
