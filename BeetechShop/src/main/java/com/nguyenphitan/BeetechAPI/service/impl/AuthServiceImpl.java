package com.nguyenphitan.BeetechAPI.service.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.nguyenphitan.BeetechAPI.custom.CustomUserDetails;
import com.nguyenphitan.BeetechAPI.entity.User;
import com.nguyenphitan.BeetechAPI.jwt.JwtTokenProvider;
import com.nguyenphitan.BeetechAPI.payload.LoginRequest;
import com.nguyenphitan.BeetechAPI.repository.UserRepository;
import com.nguyenphitan.BeetechAPI.service.AuthService;

/**
 * Auth service implements
 * @author ADMIN
 *
 */
@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JwtTokenProvider tokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	/*
	 * Check login
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public String handleLogin(String username, String password, HttpServletRequest request) {
		String jwt = null;
		try {
			LoginRequest loginRequest = new LoginRequest(username, password);
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							loginRequest.getUsername(), 
							loginRequest.getPassword()
					)
			);
			// Set authentication into Security Context
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			// Generate token
			jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
			HttpSession session = request.getSession();
			session.setAttribute("token", jwt);
			Long userId = tokenProvider.getUserIdFromJWT(jwt);
			User user = userRepository.getById(userId);
			session.setAttribute("role", user.getRole());
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return jwt;
	}

	
	/*
	 * Register new account
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public User handleRegister(String username, String password) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setRole("USER");
		userRepository.save(user);
		return user;
	}

	
	/*
	 * Logout service
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public void handleLogout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.removeAttribute("token");
		session.removeAttribute("username");
		session.removeAttribute("role");
		// Reset cookies:
		for (Cookie cookie : request.getCookies()) {
		    cookie.setValue("");
		    cookie.setMaxAge(0);
		    cookie.setPath("/");
		    response.addCookie(cookie);
		}
	}


	/*
	 * Validate token
	 * Created by: NPTAN(13/05/2022)
	 * Version: 1.0
	 */
	@Override
	public ModelAndView validateToken(String page, HttpSession session) {
		String token = (String) session.getAttribute("token");
		if(token != null) {
			return new ModelAndView("redirect:/");
		}
		return new ModelAndView(page);
	}

}
