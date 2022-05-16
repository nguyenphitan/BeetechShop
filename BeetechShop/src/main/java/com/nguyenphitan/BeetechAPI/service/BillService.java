package com.nguyenphitan.BeetechAPI.service;

import java.util.List;

import com.nguyenphitan.BeetechAPI.entity.OrderAccount;
import com.nguyenphitan.BeetechAPI.payload.BillRequest;
import com.nguyenphitan.BeetechAPI.payload.BillResponse;

public interface BillService {
	// Hiển thị danh sách tất cả hóa đơn:
	List<BillResponse> getAllBills();
	
	// Tạo hóa đơn:
	OrderAccount create(BillRequest billRequest);
	
	// Cập nhật hóa đơn:
	OrderAccount update(Long id);
}
