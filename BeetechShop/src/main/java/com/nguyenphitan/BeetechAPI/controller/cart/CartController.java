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
import com.nguyenphitan.BeetechAPI.repository.CartRepository;
import com.nguyenphitan.BeetechAPI.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	CartService cartService;
	
	@PostMapping()
	public Cart addToCart(@Valid @RequestBody ProductRequest productRequest, HttpServletRequest request)  {
		return cartService.addToCart(productRequest, request);
	}
	
	@PutMapping("/{id}")
	public Cart updateCart(@PathVariable("id") Long id, @Valid @RequestBody Cart cart) {
		return cartRepository.save(cart);
	}

	@DeleteMapping("/{id}")
	public void deleteCart(@PathVariable("id") Long id) {
		cartRepository.deleteById(id);
	}
	
}
