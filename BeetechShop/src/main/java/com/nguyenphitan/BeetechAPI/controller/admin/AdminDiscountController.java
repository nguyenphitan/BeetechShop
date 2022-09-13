package com.nguyenphitan.BeetechAPI.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.nguyenphitan.BeetechAPI.entity.discount.Discount;
import com.nguyenphitan.BeetechAPI.service.admin.DiscountService;

/*
 * Discount manager controller
 * Created by: NPTAN (15/04/2022)
 * Version: 1.0
 */
@RestController
@RequestMapping("/admin/api/v1/discounts")
public class AdminDiscountController {
	
	@Autowired
	DiscountService discountService;
	
	/*
	 * Get all
	 * Created by: NPTAN (15/04/2022)
	 * Version: 1.0
	 */
	@GetMapping()
	public List<Discount> getAlls() {
		return discountService.getAlls();
	}
	
	
	/*
	 * Get discount by id
	 * Created by: NPTAN (15/04/2022)
	 * Version: 1.0
	 */
	@GetMapping("/{id}")
	public Discount getById(@PathVariable("id") Long id) {
		return discountService.getById(id);
	}
	
	
	/*
	 * Create a new discount
	 * Created by: NPTAN (15/04/2022)
	 * Version: 1.0
	 */
	@PostMapping()
	public RedirectView addDiscount(@RequestParam("discount") Double discount, @RequestParam("value") Double value) {
		discountService.createDiscount(discount, value);
		return new RedirectView("/admin-discount");
	}
	
	
	/*
	 * Update discount with id
	 * Created by: NPTAN (15/04/2022)
	 * Version: 1.0
	 */
	@PutMapping("/{id}")
	public Discount updateDiscount(@PathVariable("id") Long id, @RequestBody Discount discount) {
		return discountService.update(id, discount);
	}
	
	
	/*
	 * Delete discount by id
	 * Created by: NPTAN (15/04/2022)
	 * Version: 1.0
	 */
	@DeleteMapping("/{id}") 
	public void deleteDiscount(@PathVariable("id") Long id) {
		discountService.delete(id);
	}
	
}
