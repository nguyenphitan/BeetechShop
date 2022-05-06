package com.nguyenphitan.BeetechAPI.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nguyenphitan.BeetechAPI.entity.OrderAccount;
import com.nguyenphitan.BeetechAPI.entity.OrderDetail;
import com.nguyenphitan.BeetechAPI.payload.BillRequest;
import com.nguyenphitan.BeetechAPI.payload.ProductRequest;
import com.nguyenphitan.BeetechAPI.repository.OrderAccountRepository;
import com.nguyenphitan.BeetechAPI.repository.OrderDetailRepository;

@RestController
@RequestMapping("/public/bill")
public class BillController {
	
	@Autowired
	private OrderAccountRepository orderAccountRepository;
	
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	/*
	 * Tạo hóa đơn
	 * 1. Lưu id hóa đơn, id khách hàng vào bảng order_account, ngày đặt hàng
	 * 2. Lưu id hóa 
	 */
	@PostMapping
	public OrderAccount create(@RequestBody BillRequest billRequest) {
		// Lấy ra useId:
		Long userId = billRequest.getUserId();
		
		// 1. Tạo một order_account -> Lưu vào db:
		OrderAccount orderAccount = new OrderAccount(userId);
		
		// 2. Lấy ra order_account_id vừa mới lưu vào db:
		OrderAccount orderAccountCurrentSave = orderAccountRepository.save(orderAccount);
		Long orderAccountId = orderAccountCurrentSave.getId();
		
		// 3. Tạo các order_detail ứng với order_account_id và danh sách sản phẩm -> Lưu vào db:
		List<ProductRequest> productRequests = billRequest.getProductRequests();
		List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
		for(ProductRequest productRequest : productRequests) {
			OrderDetail orderDetail = new OrderDetail(orderAccountId, productRequest.getIdProduct(), productRequest.getQuantitySelected());
			orderDetails.add(orderDetail);
		}
		orderDetailRepository.saveAll(orderDetails);
		
		return orderAccountCurrentSave;
	}
	
	
	/*
	 * Cập nhật hóa đơn (chỉ cập nhật status)
	 * Created by: NPTAN (06/05/2022)
	 * Version: 1.0
	 */
	@PutMapping("update/{id}")
	public OrderAccount update(@PathVariable("id") Long id) {
		OrderAccount orderAccount = orderAccountRepository.getById(id);
		orderAccount.setStatus(1);
		return orderAccountRepository.save(orderAccount);
	}
	
}
