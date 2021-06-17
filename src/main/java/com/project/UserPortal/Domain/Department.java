package com.project.UserPortal.Domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="Department")
public class Department
{
//    public Department(String name)
//    {
//        this.name=name;
//    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private Set<Employee> employeeSet=new HashSet<>();

    @OneToMany(mappedBy = "department" , cascade = CascadeType.ALL)
    private Set<Project> projectSet=new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Employee> getEmployeeSet() {
        return employeeSet;
    }

//    public void setEmployeeSet(Set<Employee> employeeSet) {
//        this.employeeSet = employeeSet;
//
//        for(Employee p : employeeSet)
//            p.setDepartment(this);
//    }

    public Set<Project> getProjectSet() {
        return projectSet;
    }

//    public void setProjectSet(Set<Project> projectSet) {
//        this.projectSet = projectSet;
//
//        for(Project p : projectSet)
//            p.setDepartment(this);
//    }
}
