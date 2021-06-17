package com.project.UserPortal.Service;

import com.project.UserPortal.Domain.User;
import com.project.UserPortal.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class UserService
{

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository)
    {
        this.userRepository=userRepository;
    }

    public User addUser(User user)
    {
        System.out.println("adduser");
        return userRepository.save(user);
    }
    public List<User> getAllUsers()
    {
        System.out.println("all users");
        List<User> users=new ArrayList<>();
        userRepository.findAll().forEach(user -> users.add(user));
        return users;
    }

    public User getUserById(int id)
    {
        Optional<User> optionalUser=userRepository.findById(id);

        return optionalUser.orElse(null);
    }
    public User getUserByName(String name)
    {
        Optional<User> optionalUser= userRepository.findByName(name);
        return optionalUser.orElse(null);

    }
    public User updateUserwithAllfields(User user)
    {
        return userRepository.save(user);
    }
    public User updateUser(User user, Integer id)
    {
        Optional<User> userOptional=userRepository.findById(id);
        User updateUser=null;
        if(!userOptional.isPresent())
            return userOptional.orElse(null);
        else
            updateUser =userOptional.get();
        if(user.getName()!=null)
            updateUser.setName(user.getName());
        if(user.getEmail()!=null)
            updateUser.setEmail(user.getEmail());
        userRepository.save(updateUser);
        return updateUser;

    }
}
