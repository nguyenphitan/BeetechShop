package com.nguyenphitan.BeetechAPI.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;

import com.nguyenphitan.BeetechAPI.entity.User;

public interface AuthService {
	// Kiểm tra đăng nhập:
	String handleLogin(String username, String password, HttpServletRequest request);
	
	// Xử lý đăng ký:
	User handleRegister(String username, String password);
	
	// Xử lý đăng xuất:
	void handleLogout(HttpServletRequest request, HttpServletResponse response);
	
	// Kiểm tra token khi người dùng nhập URL vào trang login hoặc register:
	ModelAndView validateToken(String page, HttpSession session);
	
}
