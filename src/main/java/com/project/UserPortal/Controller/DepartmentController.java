package com.project.UserPortal.Controller;

import com.project.UserPortal.DTO.DepartmentDTO;
import com.project.UserPortal.Domain.Department;
import com.project.UserPortal.Mapper.DepartmentMapper;
import com.project.UserPortal.Service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/department")
public class DepartmentController
{
    private DepartmentMapper departmentMapper;
    private DepartmentService departmentService;

    public DepartmentController(DepartmentMapper departmentMapper, DepartmentService departmentService) {
        this.departmentMapper = departmentMapper;
        this.departmentService = departmentService;
    }
    @PostMapping(path="/")
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentDTO addDepartment(@RequestBody DepartmentDTO departmentDTO)
    {
       Department department= departmentService.addDepartment(departmentMapper.convertDTOToDepartment(departmentDTO));
       return departmentMapper.convertDepartmentToDTO(department);
    }
    @DeleteMapping(path="/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void DeleteDepartment(@PathVariable int id)
    {
         departmentService.deleteDepartment(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DepartmentDTO UpdateDepartment(@PathVariable int id,@RequestBody DepartmentDTO departmentDTO)
    {
        Department department1=departmentService.updateDepartment(id,departmentMapper.convertDTOToDepartment(departmentDTO));
        return departmentMapper.convertDepartmentToDTO(department1);
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DepartmentDTO getDepartment(@PathVariable int id)
    {
        Department department=departmentService.getDepartment(id);
        return departmentMapper.convertDepartmentToDTO(department);
    }
    @GetMapping("/")
    public ResponseEntity<?> getALlDepartment()
    {
        //Set<Department> departmentSet=departmentService.getallDepartment(pageNo,pageSize);
        return new ResponseEntity<>(departmentService.getallDepartment(),HttpStatus.OK);
    }

    @GetMapping("/departmentswithprojectcount")
    public ResponseEntity<?> listDepartmentWithProjectCount()
    {
        return new ResponseEntity<>(departmentService.getDepartmentWithProjectCount(),HttpStatus.OK);
    }

    @GetMapping("/totalCost")
    public ResponseEntity<?> getTotalCost()
    {
        return new ResponseEntity<>(departmentService.getDepartmentCost(),HttpStatus.OK);
    }

    @GetMapping("/DepartmentListOnProjectCost")
    public ResponseEntity<?> getDepartmentList(@RequestParam String cost)
    {
        return new ResponseEntity<>(departmentService.getDepartmentHavingMinSalary(new Long(cost)),HttpStatus.OK);
    }

}
