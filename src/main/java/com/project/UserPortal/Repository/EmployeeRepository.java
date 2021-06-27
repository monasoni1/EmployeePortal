package com.project.UserPortal.Repository;

import com.project.UserPortal.Domain.Employee;
import com.project.UserPortal.Projection.EmployeeProject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee,Integer>
{
      @Query(value="select e.name as name ,e.id as id ,count(*) as noOfProjects from employee_project ep join employee e on ep.employee_id= e.id join project p on ep.project_id=p.id group by e.id", nativeQuery = true)
      List<EmployeeProject> EmployeewithProjectCount();
}
