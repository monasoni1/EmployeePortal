package com.project.UserPortal.Domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@EqualsAndHashCode(exclude = "projects")
@Entity
@Table(name="Employee")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Employee
{
    public Employee()
    {

    }
    public Employee(String name,Project ... projects) {
        this.name = name;
        this.projects = Stream.of(projects).collect(Collectors.toSet());
        this.projects.forEach(x -> x.getEmployees().add(this));

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String jobtitle;
    private String phoneno;
    private Long salary;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="department_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Department department;

    @ManyToMany(cascade = {CascadeType.MERGE},fetch=FetchType.EAGER)
    @JoinTable(
            name = "Employee_Project",
            joinColumns = { @JoinColumn(name = "employee_id") },
            inverseJoinColumns = { @JoinColumn(name = "project_id") }
    )
    Set<Project> projects = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }


//    public void addProject(Project p) {
//        this.projects.add(p);
//        p.getEmployees().add(this);
//    }

//    public void removeBook(Book b) {
//        this.books.remove(b);
//        b.getAuthors().remove(this);
//    }
}
