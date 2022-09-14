package com.nguyenphitan.BeetechAPI.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nguyenphitan.BeetechAPI.entity.Cart;

/**
 * Synchronized service
 * @author ADMIN
 *
 */
public interface SynchronizedService {
	/**
	 * Cart synchronized
	 * @param request
	 * @return
	 */
	List<Cart> synchronizedCart(HttpServletRequest request);
}
