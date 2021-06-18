package com.project.UserPortal.Controller;

import com.project.UserPortal.Domain.User;
import com.project.UserPortal.Service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/Users")
public class UserController
{

    private final UserService userService;
    public UserController(UserService userService)
    {
        this.userService = userService;
    }
    @PostMapping(path = "/")
    public User addUser(@RequestBody User user)
    {
        return userService.addUser(user);
    }

    @GetMapping(path="/msgs")
    public String getMsg()
    {
        return "get user";
    }
    @RequestMapping(path="/user")
    public User GetUserId(@RequestParam (required = false) Integer id, @RequestParam(required = false ) String name)
    {
        if(name!=null)
            return userService.getUserByName(name);
        return userService.getUserById(id);

    }
    @GetMapping(path="/")
    public List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @PutMapping(path="/user/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Integer id)
    {
        user.setId(id);
        return userService.updateUserwithAllfields(user);
    }

    @PatchMapping(path="/user/{id}")
    public User updateUser1( @RequestBody User user, @PathVariable Integer id)
    {
        return userService.updateUser(user, id);
    }
}
