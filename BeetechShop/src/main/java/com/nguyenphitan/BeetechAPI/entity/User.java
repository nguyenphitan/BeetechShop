package com.nguyenphitan.BeetechAPI.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.nguyenphitan.BeetechAPI.message.Message;

import lombok.Data;

/**
 * User entity
 * @author ADMIN
 *
 */
@Entity
@Table(name = "user")
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/** Username */
	@NotNull(message = Message.NOT_NULL)
	@Column(nullable = false, unique = true)
	private String username;
	
	/** Password */
	@NotNull(message = Message.NOT_NULL)
	private String password;
	
	/** User role */
	@NotBlank(message = Message.NOT_BLANK)
	private String role;

}
