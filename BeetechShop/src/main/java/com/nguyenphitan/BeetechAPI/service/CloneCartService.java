package com.nguyenphitan.BeetechAPI.service;

import javax.servlet.http.HttpServletRequest;

import com.nguyenphitan.BeetechAPI.entity.Cart;
import com.nguyenphitan.BeetechAPI.payload.ProductRequest;

/*
 * Cart clone service
 * Created by: NPTAN
 * Version: 1.0
 */
public interface CloneCartService {
	/**
	 * Add product to cart
	 * @param productRequest
	 * @param request
	 * @return
	 */
	Cart addProductToCloneCart(ProductRequest productRequest, HttpServletRequest request);
	
	/**
	 * Update product quantity
	 * @param productId
	 * @param quantityUpdate
	 * @param request
	 * @return
	 */
	Cart update(Long productId, Long quantityUpdate, HttpServletRequest request);
	
	/**
	 * Delete product by id
	 * @param productId
	 * @param request
	 */
	void deleteProduct(Long productId, HttpServletRequest request);
	
}
