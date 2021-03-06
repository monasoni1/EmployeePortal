package com.project.UserPortal.DTO;

import com.project.UserPortal.Domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO
{
    private int id;
    private String name;
    private Set<EmployeeInfoDTO> employees ;
    private int deptId;
    private Long cost;

    public Long getCost() {
        return cost;
    }
    public void setCost(Long cost) {
        this.cost = cost;
    }
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

    public Set<EmployeeInfoDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<EmployeeInfoDTO> employees) {
        this.employees = employees;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }
}
