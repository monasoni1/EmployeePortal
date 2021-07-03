package com.project.UserPortal.Repository;

import com.project.UserPortal.Domain.Project;
import com.project.UserPortal.Projection.ProjectEmployee;
import com.project.UserPortal.Projection.ProjectWithCost;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends PagingAndSortingRepository<Project,Integer>
{
    public Optional<Project> findByName(String name);


    //@Query(value="select * from project p where p.department_id in (select id from department where name=:name)",nativeQuery = true)
    @Query("select p from Project p  Join p.department pd where pd.name=?1")
    List<Project> listProjectByDepartment(@Param("name") String name);

    //@Query(value = "select p.id as id ,p.name as name,count(ep.employee_id) as noOfEmployees from Employee_Project as ep join Project p on ep.project_id=p.id join employee e on ep.employee_id=e.id group by p.id ",nativeQuery = true)
    @Query("select p.id as id ,p.name as name,count(e) as noOfEmployees from  Project p join  p.employees e  group by p.id ")
    List<ProjectEmployee> employeeCountOnProject();
    //Query q = em.createQuery(“SELECT a FROM Author a WHERE a.id IN (SELECT s.authorId FROM SpecialAuthors s)”);

    //@Query(value = "select p.id as id, p.name as name, p.cost as cost from Project p order by cost desc Limit :n,1",nativeQuery = true)
    //@Query("select p.id as id, p.name as name, p.cost as cost from Project p order by p.cost desc Limit ?n,1")
//    TypedQuery<ProjectWithCost> query=entityManager.createQuery("select p.id as id, p.name as name, p.cost as cost from Project p order by p.cost desc");
//    query.getSingleResult(1);
    @Query("select p.id as id ,  p.name as name, p.cost as cost  from Project p where :n=(select count(DISTINCT(p1.cost)) from Project p1 where p1.cost>p.cost)")
    List<ProjectWithCost> fecthNthHighestSalary( Long n);

}
