package com.project.UserPortal.Repository;

import com.project.UserPortal.Domain.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee,Integer>
{

}
