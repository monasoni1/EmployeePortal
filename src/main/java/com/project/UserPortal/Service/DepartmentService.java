package com.project.UserPortal.Service;

import com.project.UserPortal.Projection.DepartmentCost;
import com.project.UserPortal.Projection.DepartmentList;
import com.project.UserPortal.Domain.Department;
import com.project.UserPortal.Exceptions.ResourceAlreadyExists;
import com.project.UserPortal.Exceptions.ResourceNotFoundException;
import com.project.UserPortal.Mapper.DepartmentMapper;
import com.project.UserPortal.Repository.DepartmentRepository;
import com.project.UserPortal.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DepartmentService
{
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private ProjectRepository projectRepository;

    public Department addDepartment(Department department)
    {
        Optional<Department> department1=departmentRepository.findByName(department.getName());
        if(department1.isPresent())
            throw new ResourceAlreadyExists("Department Already Exist with name "+ department.getName());
        return departmentRepository.save(department);

    }
    public Department updateDepartment(int id,Department department)
    {
        Optional<Department> optionalDepartment=departmentRepository.findById(id);
        if(!optionalDepartment.isPresent())
            throw new ResourceNotFoundException("Department with id "+id+"doesn't exists!!");
        department.setId(id);
        return departmentRepository.save(department);
    }
    public void deleteDepartment(int id)
    {
        Optional<Department> department1=departmentRepository.findById(id);
        if(!department1.isPresent())
            throw new ResourceNotFoundException("Department with id "+id+"doesn't exists!!");
        departmentRepository.deleteById(id);
    }

//    public Set<Department> getallDepartment(int pageNo ,int pageSize)
//    {
//        Pageable paging = PageRequest.of(pageNo,pageSize);
//        Page<Department> page=departmentRepository.findAll(paging);
//
//        return page.toSet();
//    }

    public List<Department> getallDepartment()
    {
        List<Department> page=departmentRepository.viewAllDepartment();

        return page;
    }

    public Department getDepartment(int id)
    {
        Optional<Department> department=departmentRepository.findById(id);
        if(!department.isPresent())
            throw new ResourceNotFoundException("Department with id "+id+"doesn't exists!!");
            return department.get();
    }
    public  List<DepartmentList> getDepartmentWithProjectCount()
    {
        return departmentRepository.listDepartmentsWithProjectCount();
    }
    public List<DepartmentCost> getDepartmentCost()
    {
        return departmentRepository.departmentCost();
    }
}
