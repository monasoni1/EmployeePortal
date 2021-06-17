package com.project.UserPortal.Repository;

import com.project.UserPortal.Domain.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProjectRepository extends CrudRepository<Project,Integer>
{
    public Optional<Project> findByName(String name);
}
