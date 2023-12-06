package com.electronic.voting.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Candidate")
@Setter
@Getter
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer candidateId;
    private String symbolName;
    private String biography;

    @Column(name = "Affiliated_Party")
    private String affiliatedParty;

    @ManyToOne
    @JoinColumn(name = "Election_ID", referencedColumnName = "Election_ID")
    private Election election;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Voter_ID", referencedColumnName = "Voter_ID")
    private Voter voter;

    @Lob
    private byte[] symbol;

    @Column(name = "Created_Date")
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "Updated_Date")
    @UpdateTimestamp
    private Date updatedDate;

}
