package com.nguyenphitan.BeetechAPI.service.admin;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.nguyenphitan.BeetechAPI.entity.Product;

/**
 * Product manager service
 * @author ADMIN
 *
 */
public interface AdminProductService {
	// Get all products
	ModelAndView productPage();
	
	// Add new product
	Product addNewProduct(String name, Long price, Long quantity, MultipartFile multipartFile) throws IOException;
	
	// Delete product by id
	void deleteProduct(Long id);
}
