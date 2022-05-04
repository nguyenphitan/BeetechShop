package com.nguyenphitan.BeetechAPI.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenphitan.BeetechAPI.entity.Bill;

/**
 * Admin quản lý hóa đơn
 * Created by: NPTAN
 * Version: 1.0
 */
@RestController
@RequestMapping("/api/v1/bill")
public class BillController {
	@PostMapping()
	public Bill create() {
		
		return null;
	}
	
}
