package com.project.UserPortal.Repository;

import com.project.UserPortal.Domain.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface DepartmentRepository  extends PagingAndSortingRepository<Department,Integer>
{
    public Optional<Department> findByName(String name);

}
