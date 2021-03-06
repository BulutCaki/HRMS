package com.hrms.applicationhrms.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostListDto {

    private int id;
    private String description;
    private int positionCount;
    private Date lastApplyDate;
    private String positionName;
    private Date releaseDate;
    private String companyName;
    private String cityName;


}
