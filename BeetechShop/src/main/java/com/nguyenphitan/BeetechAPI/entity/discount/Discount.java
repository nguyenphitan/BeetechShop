package com.nguyenphitan.BeetechAPI.entity.discount;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * Discount entity
 * @author ADMIN
 *
 */
@Entity
@Table(name = "discount")
@Data
public class Discount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Value to gain discount
	 */
	@NotBlank
	private Double value;
	
	/**
	 * Discount value
	 */
	@NotBlank
	private Double discount;
}
