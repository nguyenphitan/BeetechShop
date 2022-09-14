package com.nguyenphitan.BeetechAPI.service;

import java.util.List;

import com.nguyenphitan.BeetechAPI.entity.OrderAccount;
import com.nguyenphitan.BeetechAPI.payload.BillRequest;
import com.nguyenphitan.BeetechAPI.payload.BillResponse;

/**
 * Bill service
 * @author ADMIN
 *
 */
public interface BillService {
	/*
	 * Get alls
	 */
	List<BillResponse> getAllBills();
	
	/**
	 * Create bill
	 * @param billRequest
	 * @return
	 */
	OrderAccount create(BillRequest billRequest);
	
	/**
	 * Update bill with id
	 * @param id
	 * @return
	 */
	OrderAccount update(Long id);
}
