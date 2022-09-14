package com.nguyenphitan.BeetechAPI.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nguyenphitan.BeetechAPI.entity.Cart;
import com.nguyenphitan.BeetechAPI.jwt.JwtTokenProvider;
import com.nguyenphitan.BeetechAPI.repository.CartRepository;
import com.nguyenphitan.BeetechAPI.service.SynchronizedService;

/**
 * Cart synchronized service implements
 * @author ADMIN
 *
 */
@Service
public class SynchronizedServiceImpl implements SynchronizedService {
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;

	/*
	 * Cart synchronized with cart clone after login success
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public List<Cart> synchronizedCart(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String token = (String) session.getAttribute("token");
		Long idUser = jwtTokenProvider.getUserIdFromJWT(token);
		
		// Get list product from cart clone
		List<Cart> cartsSession = (List<Cart>) session.getAttribute("cartsSession");
		
		// Update quantity with products already in cart with user id -> remove products from session
		if( cartsSession != null ) {
			List<Cart> carts = cartRepository.findByIdUser(idUser);
			for(Cart cartDB : carts) {
				Long idProDB = cartDB.getIdProduct();
				if( cartsSession != null ) {
					for(Cart cartSS : cartsSession) {
						Long idProSS = cartSS.getIdProduct();
						if( idProDB == idProSS ) {
							Long quantity = cartDB.getQuantity() + cartSS.getQuantity();
							cartDB.setQuantity(quantity);
							cartRepository.save(cartDB);
							// remove product from cart clone
							cartsSession.remove(cartSS);
							break;
						}
					}
				}
			}
			
		}
		
		// Add new to database with products not in cart with user id -> remove products from session
		if( cartsSession != null ) {
			for(Cart cart : cartsSession) {
				cart.setIdUser(idUser);
			}
			cartRepository.saveAll(cartsSession);
		}
		return cartsSession;
	}

}
