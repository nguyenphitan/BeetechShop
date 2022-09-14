package com.nguyenphitan.BeetechAPI.service.admin;

import org.springframework.web.multipart.MultipartFile;

/**
 * CSV service
 * @author ADMIN
 *
 */
public interface CSVService {
	// import csv file
	void save(MultipartFile file);
}
