package com.nguyenphitan.BeetechAPI.service.admin.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.nguyenphitan.BeetechAPI.entity.Product;
import com.nguyenphitan.BeetechAPI.repository.CartRepository;
import com.nguyenphitan.BeetechAPI.repository.ProductRepository;
import com.nguyenphitan.BeetechAPI.service.admin.AdminProductService;

/**
 * Admin product manager service implements
 * @author ADMIN
 *
 */
@Service
public class AdminProductServiceimpl implements AdminProductService {
	private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CartRepository cartRepository;
	
	/*
	 * Get alls product
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public ModelAndView productPage() {
		ModelAndView modelAndView = new ModelAndView("admin/product");
		List<Product> products = productRepository.findAll();
		modelAndView.addObject("products", products);
		return modelAndView;
	}
	
	
	/*
	 * Add new product
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public Product addNewProduct(String name, Long price, Long quantity, MultipartFile multipartFile) throws IOException {
		Path staticPath = Paths.get("src/main/resources/static");
        Path imagePath = Paths.get("img");
        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
        }
        Path file = CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(multipartFile.getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(file)) {
            os.write(multipartFile.getBytes());
        }
        
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setPhotos(imagePath.resolve(multipartFile.getOriginalFilename()).toString());
        return productRepository.save(product);
	}

	
	/*
	 * Delete product by id
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
		cartRepository.deleteByIdProduct(id);
	}


}
