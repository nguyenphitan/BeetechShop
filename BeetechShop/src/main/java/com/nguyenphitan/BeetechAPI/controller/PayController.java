package com.nguyenphitan.BeetechAPI.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenphitan.BeetechAPI.service.PayService;

@RestController
@RequestMapping("/pay")
public class PayController {
	
	@Autowired
	private PayService payService;
	
	@DeleteMapping()
	public void payment(HttpServletRequest request) {
		payService.payment(request);
	}
	
}
