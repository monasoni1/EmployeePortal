package com.project.UserPortal.Repository;

import com.project.UserPortal.Domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Integer> {
    public Optional<User> findByName(String name);
//public Optional<User> findById(Long id);
}