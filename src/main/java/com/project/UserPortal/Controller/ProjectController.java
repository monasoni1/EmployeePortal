package com.project.UserPortal.Controller;

import com.project.UserPortal.DTO.ProjectDTO;
import com.project.UserPortal.Domain.Project;
import com.project.UserPortal.Mapper.ProjectMapper;
import com.project.UserPortal.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.Set;

@RestController
@RequestMapping("/api/project")
public class ProjectController
{
    @Autowired
    private ProjectService projectservice;
    @Autowired
    private ProjectMapper projectMapper;

    @PostMapping("/")
    public ResponseEntity<?> addProject(@RequestBody ProjectDTO projectDTO)
    {
        Project project1=projectservice.addProject(projectMapper.map(projectDTO));
        return new ResponseEntity<>(projectMapper.map(project1), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@RequestBody ProjectDTO projectDTO,@PathVariable int id)
    {
        //update dept id as well
        Project project11=projectMapper.map(projectDTO);
        Project  project1=projectservice.updateProject(id,project11);
        return new ResponseEntity<>(projectMapper.map(project1), HttpStatus.OK);

    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable int id)
    {
        projectservice.deleteProject(id);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProject(@PathVariable int id)
    {
        return new ResponseEntity<>(projectservice.getProject(id),HttpStatus.NOT_FOUND);
    }
    @GetMapping("/{pageNo}/{pageSize}")
    public ResponseEntity<?> getAllProject(@PathVariable int pageNo, @PathVariable int pageSize)
    {
        Set<Project> projectSet=projectservice.getALlProjects(pageNo,pageSize);
        return new ResponseEntity<>(projectMapper.convert(projectSet),HttpStatus.OK);
    }

    @PutMapping(path="/{id}/addEmployees")
    public ResponseEntity<?> mapEmployees(@PathVariable int id, @RequestBody ProjectDTO projectDTO)
    {
        Project project=projectservice.mapEmployees(id,projectMapper.map(projectDTO));
        return new ResponseEntity<>(projectMapper.map(project),HttpStatus.OK);
    }
}
