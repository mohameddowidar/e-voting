package com.electronic.voting.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Complain {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, name = "complain_id")
	Long complainId;
	
	String complainTitle;
	String complainSubject;
	
	Date complainDate;
	
	Date reportDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "complainCity", referencedColumnName = "cityId")
	City complainCity;
	
	String complainAddress;
	
	String complainReporterName;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "statusId", referencedColumnName = "statusId")
	Status status;

}
