package com.project.UserPortal.Projection;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public interface DepartmentList
{
    String getName();
    int getId();
    int getNoOfProjects();


}
