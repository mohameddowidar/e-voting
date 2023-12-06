package com.electronic.voting.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Vote")
@Setter
@Getter
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Voter_ID")
    private Integer voterId;

    @Column(name = "Candidate_ID")
    private Integer candidateId;

    @Column(name = "Election_ID")
    private Integer electionId;

    @Column(name = "Time_Stamp")
    private Date timeStamp;

    @Column(name = "Created_Date")
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "Updated_Date")
    @UpdateTimestamp
    private Date updatedDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Voter_ID", insertable = false, updatable = false)
    private Voter voter;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Candidate_ID", insertable = false, updatable = false)
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Election_ID", insertable = false, updatable = false)
    private Election election;

}
