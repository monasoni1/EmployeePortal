package com.project.UserPortal.Repository;

import com.project.UserPortal.Projection.DepartmentCost;
import com.project.UserPortal.Projection.DepartmentList;
import com.project.UserPortal.Domain.Department;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository  extends PagingAndSortingRepository<Department,Integer>
{
    public Optional<Department> findByName(String name);

    @Query(value="Select * from Department order by name",nativeQuery = true)
    List<Department> viewAllDepartment();

    //@Query(value ="select d.id,d.name,count(*) as noOfProjects from Department d join Project p where p.department_id=d.id group by d.id", nativeQuery = true)
    @Query("select d.id as id,d.name as name,count(d) as noOfProjects from Project p join p.department d  group by d.id")
    List<DepartmentList> listDepartmentsWithProjectCount();

    @Query("select d.id as id,d.name as name,sum(p.cost) as cost from Project p join p.department d  group by d.id")
    List<DepartmentCost> departmentCost();

}
