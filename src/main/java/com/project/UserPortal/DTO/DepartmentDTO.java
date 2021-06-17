package com.project.UserPortal.DTO;

import com.project.UserPortal.Domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data

@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO
{
    private int id;
    private String name;
    private Set<EmployeeDTO> employeeSet;
    private Set<ProjectDTO> projectSet;

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

    public Set<ProjectDTO> getProjectSet() {
        return projectSet;
    }

    public void setProjectSet(Set<ProjectDTO> projectSet) {
        this.projectSet = projectSet;
    }

    public Set<EmployeeDTO> getEmployeeSet() {
        return employeeSet;
    }

    public void setEmployeeSet(Set<EmployeeDTO> employeeSet) {
        this.employeeSet = employeeSet;
    }
}
