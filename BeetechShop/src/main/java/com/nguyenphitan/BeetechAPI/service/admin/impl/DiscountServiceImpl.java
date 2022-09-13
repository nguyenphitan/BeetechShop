package com.nguyenphitan.BeetechAPI.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nguyenphitan.BeetechAPI.entity.discount.Discount;
import com.nguyenphitan.BeetechAPI.repository.discount.DiscountRepository;
import com.nguyenphitan.BeetechAPI.service.admin.DiscountService;

/**
 * Admin discount manager service implements
 * @author ADMIN
 *
 */
@Service
public class DiscountServiceImpl implements DiscountService {
	
	@Autowired
	DiscountRepository discountRepository;


	/*
	 * Get alls discount
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public List<Discount> getAlls() {
		return discountRepository.findAll();
	}

	
	/*
	 * Get discount by id
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public Discount getById(Long id) {
		return discountRepository.getById(id);
	}

	
	/*
	 * Create new discount
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public Discount createDiscount(Double discount, Double value) {
		Discount newDiscount = new Discount();
		newDiscount.setDiscount(discount);
		newDiscount.setValue(value);
		return discountRepository.save(newDiscount);
	}

	
	/*
	 * Update discount with id
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public Discount update(Long id, Discount discount) {
		return discountRepository.save(discount);
	}

	
	/*
	 * Delete discount by id
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public void delete(Long id) {
		discountRepository.deleteById(id);
	}
	
}
