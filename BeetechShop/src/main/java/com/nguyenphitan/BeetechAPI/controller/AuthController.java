package com.nguyenphitan.BeetechAPI.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.nguyenphitan.BeetechAPI.service.AuthService;

import lombok.RequiredArgsConstructor;

/**
 * Đăng nhập, đăng ký, đăng xuất.
 * Created by: NPTAN
 * Version: 1.0
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	
	/*
	 * Đăng nhập, xác thực
	 * Created by: NPTAN
	 * Version: 1.0
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
	 * Đăng ký thêm mới tài khoản
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@PostMapping("/register")
	public RedirectView createUser(@RequestParam("username") String username, @RequestParam("password") String password) {
		authService.handleRegister(username, password);
		return new RedirectView("/auth/login");
	}
	
	
	/*
	 * Đăng xuất tài khoản
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/logout")
	public RedirectView logout(HttpServletRequest request, HttpServletResponse response) {
		authService.handleLogout(request, response);
		return new RedirectView("/auth/login");
	}
	
	
}
