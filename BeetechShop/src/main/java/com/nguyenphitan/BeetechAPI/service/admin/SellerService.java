package com.nguyenphitan.BeetechAPI.service.admin;

import java.util.List;

import com.nguyenphitan.BeetechAPI.entity.User;

/**
 * Seller manager service
 * @author ADMIN
 *
 */
public interface SellerService {
	/**
	 * Get alls
	 * @return
	 */
	List<User> getAllSellers();
	
	/**
	 * Add a new seller
	 * @param id
	 * @return
	 */
	int addSeller(int id);
	
	/**
	 * Delete by id
	 * @param id
	 */
	void deleteSeller(int id);
	
}
