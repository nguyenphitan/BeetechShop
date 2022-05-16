package com.nguyenphitan.BeetechAPI.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.nguyenphitan.BeetechAPI.entity.Cart;

public interface SynchronizedService {
	List<Cart> synchronizedCart(HttpServletRequest request);
}
