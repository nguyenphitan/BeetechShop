package com.nguyenphitan.BeetechAPI.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.nguyenphitan.BeetechAPI.entity.Cart;
import com.nguyenphitan.BeetechAPI.payload.CartResponse;
import com.nguyenphitan.BeetechAPI.payload.ProductRequest;

/**
 * Cart service
 * @author ADMIN
 *
 */
public interface CartService {
	/**
	 * Get all products from cart
	 * @param modelAndView
	 * @param request
	 * @return
	 */
	List<CartResponse> getAllCart(ModelAndView modelAndView, HttpServletRequest request);
	
	/**
	 * Add product to cart
	 * @param productRequest
	 * @param request
	 * @return
	 */
	Cart addToCart(ProductRequest productRequest, HttpServletRequest request);

	/**
	 * Payment handler
	 * @param modelAndView
	 * @param listCartResponses
	 * @param request
	 */
	void handlePayment(ModelAndView modelAndView, List<CartResponse> listCartResponses, HttpServletRequest request);

	/**
	 * Count cart size
	 * @param request
	 */
	void countCartSize(HttpServletRequest request);
	
	/**
	 * Update product quantity from cart
	 * @param id
	 * @param quantityUpdate
	 * @return
	 */
	Cart update(Long id, Long quantityUpdate);
}
