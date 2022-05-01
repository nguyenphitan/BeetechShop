package com.nguyenphitan.BeetechAPI.controller.cart;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenphitan.BeetechAPI.entity.Cart;
import com.nguyenphitan.BeetechAPI.payload.ProductRequest;
import com.nguyenphitan.BeetechAPI.service.CloneCartService;

@RestController
@RequestMapping("/clone")
public class CloneCartController {
	
	@Autowired
	CloneCartService cloneCartService;
	
	@PostMapping("/cart")
	public Cart addProductToCloneCart(@Valid @RequestBody ProductRequest productRequest, HttpServletRequest request) {
		return cloneCartService.addProductToCloneCart(productRequest, request);
	}
	
	@DeleteMapping("/cart/{id}")
	public void deleteCartSession(@PathVariable("id") Long productId, HttpServletRequest request) {
		cloneCartService.deleteProduct(productId, request);
	}
	
}
