package com.nguyenphitan.BeetechAPI.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.nguyenphitan.BeetechAPI.message.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Order detail
 * @author ADMIN
 *
 */
@Entity
@Table(name = "order_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = Message.NOT_NULL)
	private Long orderAccountId;
	
	@NotNull(message = Message.NOT_NULL)
	private Long productId;
	
	private Long quantity;
	
	public OrderDetail(Long orderAccountId, Long productId, Long quantity) {
		this.orderAccountId = orderAccountId;
		this.productId = productId;
		this.quantity = quantity;
	}
}
