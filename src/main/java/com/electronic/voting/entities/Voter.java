package com.electronic.voting.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Voter")
@Setter
@Getter
public class Voter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Voter_ID")
    private Long voterId;

    private String name;
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    private String address;
    private String email;
    private String phoneNumber;
    private String nationalId;
    private Integer age;
    @Lob
    private byte[] faceBiometricData;
    @Lob
    private byte[] photo;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updatedDate;

    @OneToMany(mappedBy = "voter", fetch = FetchType.EAGER)
    private Set<Candidate> candidates;
}
