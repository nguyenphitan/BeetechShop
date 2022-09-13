package com.nguyenphitan.BeetechAPI.service.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nguyenphitan.BeetechAPI.entity.User;
import com.nguyenphitan.BeetechAPI.repository.UserRepository;
import com.nguyenphitan.BeetechAPI.service.admin.SellerService;

/**
 * Admin seller manager service implements
 * @author ADMIN
 *
 */
@Service
public class SellerServiceImpl implements SellerService {
	
	@Autowired
	UserRepository userRepository;

	/**
	 * Get all sellers
	 */
	@Override
	public List<User> getAllSellers() {
		List<User> accounts = userRepository.findByRole("SELLER");
		return accounts;
	}

	/**
	 * Add new seller
	 * @param id
	 */
	@Override
	public int addSeller(int id) {
		Integer result = userRepository.addSeller("SELLER", id);
		return result;
	}

	/**
	 * Delete seller by id
	 * @param id
	 */
	@Override
	public void deleteSeller(int id) {
		userRepository.addSeller("USER", id);
	}

	
}
