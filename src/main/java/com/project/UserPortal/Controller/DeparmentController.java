package com.project.UserPortal.Controller;

import com.project.UserPortal.DTO.DepartmentDTO;
import com.project.UserPortal.Domain.Department;
import com.project.UserPortal.Mapper.DepartmentMapper;
import com.project.UserPortal.Service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/department")
public class DeparmentController
{
    private DepartmentMapper departmentMapper;
    private DepartmentService departmentService;

    public DeparmentController(DepartmentMapper departmentMapper, DepartmentService departmentService) {
        this.departmentMapper = departmentMapper;
        this.departmentService = departmentService;
    }

//    @ExceptionHandler(NotFoundException.class)
//    public String handleNotFound(Exception exception)
//    {
//        System.out.println(exception.getMessage());
//        return exception.getMessage();
//    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(BadRequestException.class)
//    public String badRequestFound(Exception exception)
//    {
//        System.out.println(exception.getMessage());
//        return exception.getMessage();
//    }
    @PostMapping(path="/")
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentDTO addDeparment(@RequestBody DepartmentDTO departmentDTO)
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
    @GetMapping("/{pageNo}/{pageSize}")
    public ResponseEntity<?> getALlDepartment(@PathVariable int pageNo , @PathVariable int pageSize)
    {
        Set<Department> departmentSet=departmentService.getallDepartment(pageNo,pageSize);

        return new ResponseEntity<>(departmentMapper.map1(departmentSet),HttpStatus.OK);
    }

}
