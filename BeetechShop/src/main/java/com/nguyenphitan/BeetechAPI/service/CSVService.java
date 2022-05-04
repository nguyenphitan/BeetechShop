package com.nguyenphitan.BeetechAPI.service;

import org.springframework.web.multipart.MultipartFile;

public interface CSVService {
	// Lưu thông tin file vào bảng:
	void save(MultipartFile file);
}
