package com.nguyenphitan.BeetechAPI.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Payment service
 * @author ADMIN
 *
 */
public interface PayService {
	/**
	 * Payment bill
	 * @param request
	 */
	void payment(HttpServletRequest request);
}
