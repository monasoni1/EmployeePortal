package com.project.UserPortal.Repository;

import com.project.UserPortal.Domain.Project;
import com.project.UserPortal.Projection.ProjectEmployee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends PagingAndSortingRepository<Project,Integer>
{
    public Optional<Project> findByName(String name);


    //@Query(value="select * from project p where p.department_id in (select id from department where name=:name)",nativeQuery = true)
    @Query("select p from Project p  Join p.department pd where pd.name=?1")
    List<Project> listProjectByDepartment(@Param("name") String name);

    //@Query(value = "select p.id as id ,p.name as name,count(ep.employee_id) as noOfEmployees from Employee_Project as ep join Project p on ep.project_id=p.id join employee e on ep.employee_id=e.id group by p.id ",nativeQuery = true)
    @Query("select p.id as id ,p.name as name,count(p) as noOfEmployees from  Project p  join employee e  group by p.id ")
    List<ProjectEmployee> employeeCountOnProject();
    //Query q = em.createQuery(“SELECT a FROM Author a WHERE a.id IN (SELECT s.authorId FROM SpecialAuthors s)”);
}
