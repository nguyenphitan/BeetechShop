package com.nguyenphitan.BeetechAPI.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.nguyenphitan.BeetechAPI.service.admin.SellerService;

/**
 * Seller manager controller
 * Created by: NPTAN (08/05/2022)
 * Version: 1.0
 */
@RestController
@RequestMapping("/admin/seller")
public class AdminSellerController {
	
	@Autowired
	SellerService sellerService;
	
	/*
	 * Get all 
	 * Created by: NPTAN (08/05/2022)
	 * Version: 1.0
	 */
	@GetMapping
	public ModelAndView sellerPage() {
		ModelAndView modelAndView = new ModelAndView("admin/seller");
		modelAndView.addObject("sellers", sellerService.getAllSellers());
		return modelAndView;
	}
	
	/*
	 * Add a new seller (add role SELLER into account)
	 * Created by: NPTAN (08/05/2022)
	 * Version: 1.0
	 */
	@PostMapping
	public Integer addSeller(@RequestBody int id) {
		return sellerService.addSeller(id);
	}
	
	/*
	 * Delete seller by id (delete only role, not delete account)
	 * Created by: NPTAN (08/05/2022)
	 * Version: 1.0 
	 */
	@DeleteMapping("/{id}")
	public void deleteSeller(@PathVariable("id") int id) {
		sellerService.deleteSeller(id);
	}
	
}
