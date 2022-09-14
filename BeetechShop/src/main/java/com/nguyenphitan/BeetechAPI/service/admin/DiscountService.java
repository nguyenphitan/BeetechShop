package com.nguyenphitan.BeetechAPI.service.admin;

import java.util.List;

import com.nguyenphitan.BeetechAPI.entity.discount.Discount;

/**
 * Discount service
 * @author ADMIN
 *
 */
public interface DiscountService {
	/**
	 * Get all discounts
	 * @return
	 */
	List<Discount> getAlls();

	/**
	 * Get discount by id
	 * @param id
	 * @return
	 */
	Discount getById(Long id);
	
	/**
	 * Create a new discount
	 * @param discount
	 * @param value
	 * @return
	 */
	Discount createDiscount(Double discount, Double value);
	
	/**
	 * Update discount with id
	 * @param id
	 * @param discount
	 * @return
	 */
	Discount update(Long id, Discount discount);
	
	/**
	 * Delete by id
	 * @param id
	 */
	void delete(Long id);
	
}
