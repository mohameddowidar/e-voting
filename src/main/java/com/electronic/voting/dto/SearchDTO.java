package com.electronic.voting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchDTO {
    private String title;
    private String nationalId;
    private Date fromDate;
    private Date toDate;
    private Integer candidateId;
    private Integer electionId;
}
