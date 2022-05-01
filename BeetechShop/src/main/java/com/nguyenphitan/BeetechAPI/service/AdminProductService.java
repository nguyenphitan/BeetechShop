package com.nguyenphitan.BeetechAPI.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface AdminProductService {
	// Thêm một sản phẩm mới:
	void addNewProduct(String name, Long price, Long quantity, MultipartFile multipartFile) throws IOException;
	
	// Xóa sản phẩm khỏi gian hàng:
	void deleteProduct(Long id);
}
