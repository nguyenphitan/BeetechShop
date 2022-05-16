package com.nguyenphitan.BeetechAPI.service;

import java.util.Map;

public interface VNPayService {
	
	// Load dữ liệu cho trang kết quả thanh toán online:
	Map<String, String> vnpayResponse(String amount, String bankCode, String bankTranNo, String cardType, String orderInfo,
			String payDate, String responseCode, String tmnCode, String transactionNo, String transactionStatus,
			String txnRef, String secureHash);
}
