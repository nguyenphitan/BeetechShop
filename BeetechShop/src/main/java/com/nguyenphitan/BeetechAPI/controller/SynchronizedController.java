package com.nguyenphitan.BeetechAPI.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.nguyenphitan.BeetechAPI.entity.Cart;
import com.nguyenphitan.BeetechAPI.jwt.JwtTokenProvider;
import com.nguyenphitan.BeetechAPI.repository.CartRepository;

@RestController
@RequestMapping("/synchronized")
public class SynchronizedController {
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	/*
	 * Đồng bộ giỏ hàng sau khi đăng nhập
	 */
	@GetMapping("/cart")
	public RedirectView synchronizedCart(HttpServletRequest request) {
		// Lấy user id từ mã token:
		HttpSession session = request.getSession();
		String token = (String) session.getAttribute("token");
		Long idUser = jwtTokenProvider.getUserIdFromJWT(token);
		
		// Get danh sách giỏ hàng -> đồng bộ
		List<Cart> cartsSession = (List<Cart>) session.getAttribute("cartsSession");
		
		// 1. Update số lượng với những sản phẩm đã có trong giỏ hàng ứng với user id -> xóa sản phẩm khỏi session
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
							// Xóa sản phẩm khỏi session:
							cartsSession.remove(cartSS);
							break;
						}
					}
				}
			}
			
		}
		
		// 2. Thêm mới vào database với những sản phẩm chưa có trong giỏ hàng ứng với user id -> xóa sản phẩm khỏi session
		if( cartsSession != null ) {
			for(Cart cart : cartsSession) {
				cart.setIdUser(idUser);
			}
			cartRepository.saveAll(cartsSession);
		}
		return new RedirectView("/");
	}
	
}
