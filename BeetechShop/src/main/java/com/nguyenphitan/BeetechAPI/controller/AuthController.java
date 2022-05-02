package com.nguyenphitan.BeetechAPI.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.nguyenphitan.BeetechAPI.entity.User;
import com.nguyenphitan.BeetechAPI.service.AuthService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	
	/*
	 * Đăng nhập, xác thực:
	 */
	@PostMapping("/login")
	public RedirectView authenticateUser(
		@RequestParam("username") String username, 
		@RequestParam("password") String password, 
		HttpServletRequest request
	) {
		authService.handleLogin(username, password, request);
		return new RedirectView("/synchronized/cart");
	}
	
	/*
	 * Đăng ký tài khoản
	 */
	@PostMapping("/register")
	public RedirectView createUser(@RequestParam("username") String username, @RequestParam("password") String password) {
		User user = authService.handleRegister(username, password);
		return new RedirectView("/auth/login");
	}
	
	/*
	 * Đăng xuất -> xóa token khỏi session
	 */
	@GetMapping("/logout")
	public RedirectView logout(HttpServletRequest request, HttpServletResponse response) {
		authService.handleLogout(request, response);
		return new RedirectView("/auth/login");
	}
	
	
}
