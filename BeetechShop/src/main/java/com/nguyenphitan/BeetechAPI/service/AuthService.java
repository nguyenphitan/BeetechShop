package com.nguyenphitan.BeetechAPI.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import com.nguyenphitan.BeetechAPI.entity.User;

/**
 * Auth service
 * @author ADMIN
 *
 */
public interface AuthService {
	/**
	 * Check login
	 * @param username
	 * @param password
	 * @param request
	 * @return
	 */
	String handleLogin(String username, String password, HttpServletRequest request);
	
	/**
	 * Register new account
	 * @param username
	 * @param password
	 * @return
	 */
	User handleRegister(String username, String password);
	
	/**
	 * Logout service
	 * @param request
	 * @param response
	 */
	void handleLogout(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * Validate token
	 * @param page
	 * @param session
	 * @return
	 */
	ModelAndView validateToken(String page, HttpSession session);
	
}
