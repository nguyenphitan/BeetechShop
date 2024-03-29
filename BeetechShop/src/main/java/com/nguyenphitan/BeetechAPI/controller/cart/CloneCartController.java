package com.nguyenphitan.BeetechAPI.controller.cart;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenphitan.BeetechAPI.entity.Cart;
import com.nguyenphitan.BeetechAPI.payload.ProductRequest;
import com.nguyenphitan.BeetechAPI.service.CloneCartService;

/**
 * Clone cart (Not logged in)
 * Created by: NPTAN (10/04/2022)
 * Version: 1.0
 */
@RestController
@RequestMapping("/clone/cart")
public class CloneCartController {
	
	@Autowired
	CloneCartService cloneCartService;
	
	/*
	 * Add product to cart
	 * Created by: NPTAN (10/04/2022)
	 * Version: 1.0
	 */
	@PostMapping
	public Cart addProductToCloneCart(@Valid @RequestBody ProductRequest productRequest, HttpServletRequest request) {
		return cloneCartService.addProductToCloneCart(productRequest, request);
	}
	
	
	/*
	 * Update product quantity
	 * Created by: NPTAN (10/04/2022)
	 * Version: 1.0
	 */
	@PutMapping("/{id}")
	public Cart updateCartSession(@PathVariable("id") Long productId, @RequestBody Long quantityUpdate, HttpServletRequest request) {
		return cloneCartService.update(productId, quantityUpdate, request);
	}
	
	
	/*
	 * Delete product from cart
	 * Created by: NPTAN (10/04/2022)
	 * Version: 1.0
	 */
	@DeleteMapping("/{id}")
	public void deleteCartSession(@PathVariable("id") Long productId, HttpServletRequest request) {
		cloneCartService.deleteProduct(productId, request);
	}
	
}
