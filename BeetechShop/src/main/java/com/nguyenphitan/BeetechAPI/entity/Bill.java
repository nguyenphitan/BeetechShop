package com.nguyenphitan.BeetechAPI.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Entity
@Table(name = "bill")
@Data
public class Bill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private Long idProduct;
	
	@NotBlank
	private Long idUser;
	
	@Temporal(TemporalType.DATE)
	private Date date;
}
