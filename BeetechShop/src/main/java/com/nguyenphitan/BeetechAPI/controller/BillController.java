package com.nguyenphitan.BeetechAPI.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenphitan.BeetechAPI.entity.Bill;

@RestController
@RequestMapping("/api/v1/bill")
public class BillController {
	@PostMapping()
	public Bill create() {
		
		return null;
	}
	
}
