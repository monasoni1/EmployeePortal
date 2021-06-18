package com.project.UserPortal.Repository;

import com.project.UserPortal.Domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ProjectRepository extends PagingAndSortingRepository<Project,Integer>
{
    public Optional<Project> findByName(String name);
}
