package com.nguyenphitan.BeetechAPI.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.nguyenphitan.BeetechAPI.entity.Cart;
import com.nguyenphitan.BeetechAPI.payload.ProductRequest;
import com.nguyenphitan.BeetechAPI.service.CloneCartService;

/**
 * Clone cart service implements
 * @author ADMIN
 *
 */
@Service
public class CloneCartServiceImpl implements CloneCartService {

	/*
	 * Add product to cart
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public Cart addProductToCloneCart(ProductRequest productRequest, HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<Cart> cartsSession = null;
		if( session.getAttribute("cartsSession") != null ) {
			cartsSession = (List<Cart>) session.getAttribute("cartsSession");
			for(Cart cart : cartsSession) {		// update product quantity
				if( cart.getIdProduct() == productRequest.getIdProduct() ) {
					cart.setQuantity( cart.getQuantity() + productRequest.getQuantitySelected() );
					session.setAttribute("cartsSession", cartsSession);
					return cart;
				}
			}
		}
		else {
			cartsSession = new ArrayList<Cart>();
		}
		// Add new product
		Cart cart = new Cart();
		cart.setIdProduct(productRequest.getIdProduct());
		cart.setIdUser(-1L);
		cart.setQuantity(productRequest.getQuantitySelected());
		
		cartsSession.add(cart);
		session.setAttribute("cartsSession", cartsSession);
		return cart;
	}
	
	
	/*
	 * Update product quantity
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public Cart update(Long productId, Long quantityUpdate, HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<Cart> carts = (List<Cart>) session.getAttribute("cartsSession");
		for(Cart cart : carts) {
			if( cart.getIdProduct() == productId ) {
				Long quantityCurrent = cart.getQuantity();
				cart.setQuantity(quantityCurrent + quantityUpdate);
				return cart;
			}
		}
		return null;
	}

	
	/*
	 * Delete product from cart
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public void deleteProduct(Long productId, HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<Cart> carts = (List<Cart>) session.getAttribute("cartsSession");
		for(Cart cart : carts) {
			Long proId = cart.getIdProduct();
			if( productId == proId ) {
				carts.remove(cart);
				break;
			}
		}
	}

}
