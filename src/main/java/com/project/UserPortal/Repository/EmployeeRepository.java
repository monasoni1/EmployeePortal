package com.project.UserPortal.Repository;

import com.project.UserPortal.Domain.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee,Integer>
{

}
