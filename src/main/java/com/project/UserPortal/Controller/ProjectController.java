package com.project.UserPortal.Controller;

import com.project.UserPortal.Domain.Project;
import com.project.UserPortal.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/api/project")
public class ProjectController
{
    @Autowired
    private ProjectService projectservice;

    @PostMapping("/")
    public ResponseEntity<?> addProject(@RequestBody Project project)
    {
        Project project1=projectservice.addProject(project);
        if(project1!=null)
            return new ResponseEntity<>(project1, HttpStatus.CREATED);
        return new ResponseEntity<>("Department  not correct",HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable int id)
    {
        boolean res=projectservice.deleteProject(id);
        if(res)
            return new ResponseEntity<>("succeeded",HttpStatus.NO_CONTENT);
        return new ResponseEntity<>("Id not found",HttpStatus.NOT_FOUND);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@RequestBody Project project,@PathVariable int id)
    {
        Project  project1=projectservice.updateProject(id,project);
        if(project1!=null)
        return new ResponseEntity<>(project1, HttpStatus.OK);
        return new ResponseEntity<>("Employee id is not valid",HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProject(@PathVariable int id)
    {
        Project project= projectservice.getProject(id);
        if(project!=null)
            return new ResponseEntity<>(project,HttpStatus.FOUND);
        return new ResponseEntity<>("Employee id is not valid",HttpStatus.NOT_FOUND);

    }
    @GetMapping("/")
    public ResponseEntity<?> getAllProject()
    {
        return  new ResponseEntity<>(projectservice.getALlProjects(),HttpStatus.OK);
    }
}
