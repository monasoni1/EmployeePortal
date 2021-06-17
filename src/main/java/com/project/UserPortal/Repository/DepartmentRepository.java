package com.project.UserPortal.Repository;

import com.project.UserPortal.Domain.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DepartmentRepository  extends CrudRepository<Department,Integer>
{
    public Optional<Department> findByName(String name);

}
