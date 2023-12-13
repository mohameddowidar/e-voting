package com.electronic.voting.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.primefaces.model.charts.pie.PieChartModel;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Election")
@Setter
@Getter
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Election_ID")
    private Integer electionId;

    private String name;
    private String description;

    @Column(name = "Start_Date")
    private Date startDate;

    @Column(name = "End_Date")
    private Date endDate;

    @Column(name = "Election_Type")
    private String electionType;
    private Integer year;

    @Column(name = "Created_Date")
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "Updated_Date")
    @UpdateTimestamp
    private Date updatedDate;

    @OneToMany(mappedBy = "election", fetch = FetchType.EAGER)
    private Set<Candidate> candidates;


    @Transient
    private PieChartModel pieModel;

}
