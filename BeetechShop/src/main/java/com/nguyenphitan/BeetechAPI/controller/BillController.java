package com.nguyenphitan.BeetechAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenphitan.BeetechAPI.entity.OrderAccount;
import com.nguyenphitan.BeetechAPI.payload.BillRequest;
import com.nguyenphitan.BeetechAPI.service.BillService;

/**
 * Bill manager controller
 * @author ADMIN
 *
 */
@RestController
@RequestMapping("/public/bill")
public class BillController {
	
	@Autowired
	private BillService billService;
	
	/*
	 * Create a new bill
	 */
	@PostMapping
	public OrderAccount create(@RequestBody BillRequest billRequest) {
		return billService.create(billRequest);
	}
	
	
	/*
	 * Update bill by id (update only status)
	 * Created by: NPTAN (06/05/2022)
	 * Version: 1.0
	 */
	@PutMapping("update/{id}")
	public OrderAccount update(@PathVariable("id") Long id) {
		return billService.update(id);
	}
	
}
