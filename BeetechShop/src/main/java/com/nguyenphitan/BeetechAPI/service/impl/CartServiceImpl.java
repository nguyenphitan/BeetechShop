package com.nguyenphitan.BeetechAPI.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.nguyenphitan.BeetechAPI.entity.Cart;
import com.nguyenphitan.BeetechAPI.entity.Product;
import com.nguyenphitan.BeetechAPI.entity.User;
import com.nguyenphitan.BeetechAPI.entity.discount.Discount;
import com.nguyenphitan.BeetechAPI.jwt.JwtTokenProvider;
import com.nguyenphitan.BeetechAPI.payload.CartResponse;
import com.nguyenphitan.BeetechAPI.payload.ProductRequest;
import com.nguyenphitan.BeetechAPI.repository.CartRepository;
import com.nguyenphitan.BeetechAPI.repository.ProductRepository;
import com.nguyenphitan.BeetechAPI.repository.UserRepository;
import com.nguyenphitan.BeetechAPI.repository.discount.DiscountRepository;
import com.nguyenphitan.BeetechAPI.service.CartService;

/**
 * Cart service implements
 * @author ADMIN
 *
 */
@Service
public class CartServiceImpl implements CartService {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	DiscountRepository discountRepository;
	
	
	/*
	 * Get all products in cart
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public List<CartResponse> getAllCart(ModelAndView modelAndView, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String token = (String) session.getAttribute("token");
		
		List<CartResponse> listProducts = new ArrayList<CartResponse>();
		
		// Get list product from clone cart if token is null
		if(token == null) {
			List<Cart> cartsSession = (List<Cart>) session.getAttribute("cartsSession");
			if( cartsSession == null ) {
				modelAndView.addObject("listProducts", null);
				session.setAttribute("listProducts", null);
				return null;
			}
			for(Cart cart: cartsSession) {
				Long idCart = cart.getId();
				Long idProduct = cart.getIdProduct();
				Long quantity = cart.getQuantity();
				Product product = productRepository.getById(idProduct);
				CartResponse cartResponse = new CartResponse(idCart, product, quantity);
				listProducts.add(cartResponse);
			}
		}
		else {	
			// Get list product from cart if token is valid
			Long idUser = jwtTokenProvider.getUserIdFromJWT(token);
			User user = userRepository.getById(idUser);
			List<Cart> listCarts = cartRepository.findByIdUser(idUser);
			if( listCarts != null ) {
				for(Cart cart: listCarts) {
					Long idCart = cart.getId();
					Long idProduct = cart.getIdProduct();
					Long quantity = cart.getQuantity();
					Product product = productRepository.getById(idProduct);
					CartResponse cartResponse = new CartResponse(idCart, product, quantity);
					listProducts.add(cartResponse);
				}							
			}
			modelAndView.addObject("userInfo", user);
			session.setAttribute("idUser", idUser);
		
		}
		
		session.setAttribute("listProducts", listProducts);
		modelAndView.addObject("listProducts", listProducts);
		session.setAttribute("cartSize", listProducts.size());
		handlePayment(modelAndView, listProducts, request);
		
		return listProducts;
	}

	
	/*
	 * Payment service
	 * 1. Calculate discount
	 * 2. Calculate total price after sub discount
	 * 3. Suggestions next discount
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public void handlePayment(ModelAndView modelAndView, List<CartResponse> listCartResponses,
			HttpServletRequest request) {
		// Total price
		Double totalCart = 0D;
		for(CartResponse cart : listCartResponses) {
			Long productPrice = cart.getProduct().getPrice();
			Long quantity = cart.getQuantity();
			Long totalPrice = quantity * productPrice;
			totalCart += totalPrice;
		}
		
		// Suggestions discount
		// 1. Get current discount: (if any)
		Double currentDiscount = 0D;
		Double nextDiscount = 0D;
		Double nextValue = 0D;
		List<Discount> discounts = discountRepository.findAll();
		discounts.sort(Comparator.comparing(Discount::getValue));
		
		if( totalCart < discounts.get(0).getValue() ) {
			nextDiscount = discounts.get(0).getDiscount();
			nextValue = discounts.get(0).getValue();
		} else {
			for ( int i = 1 ; i < discounts.size() ; i++ ) {
				if( totalCart >= discounts.get(i-1).getValue() && totalCart < discounts.get(i).getValue() ) {
					currentDiscount = discounts.get(i-1).getDiscount();
					nextDiscount = discounts.get(i).getDiscount();
					nextValue = discounts.get(i).getValue();
					break;
				}
			}
		}
		
		// 2.  Calculate total price after sub discount:
		Double discountValue = (totalCart * currentDiscount)/100;
		Long realCart = Math.round(totalCart - discountValue);
		
		// 3. Suggestions next discount and value:
		Double valueToNextDiscount = nextValue - totalCart; 
		
		modelAndView.addObject("currentDiscount", currentDiscount);
		modelAndView.addObject("nextDiscount", nextDiscount);
		modelAndView.addObject("discountValue", discountValue);
		modelAndView.addObject("valueToNextDiscount", valueToNextDiscount);
		modelAndView.addObject("totalCart", totalCart);
		modelAndView.addObject("realCart", realCart);
		
		HttpSession session = request.getSession();
		session.setAttribute("realCart", realCart);
	}

	/*
	 * Add product to cart
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public Cart addToCart(ProductRequest productRequest, HttpServletRequest request) {
		// Get user id from token:
		HttpSession session = request.getSession();
		String token = (String) session.getAttribute("token");
		Long idUser = jwtTokenProvider.getUserIdFromJWT(token);
		Long idProduct = productRequest.getIdProduct();
		Long quantitySelected = productRequest.getQuantitySelected();
		
		// Update cart quantity:
		List<Cart> listCarts = cartRepository.findByIdProduct(idProduct);
		if( !listCarts.isEmpty() ) {
			for(Cart cart : listCarts) {
				if( cart.getIdUser() == idUser ) {
					Long quantity = cart.getQuantity() + quantitySelected;
					cart.setQuantity(quantity);
					return cartRepository.save(cart);
				}
			}
		}
		Cart cart = new Cart();
		cart.setIdProduct(idProduct);
		cart.setIdUser(idUser);
		cart.setQuantity(quantitySelected);		
		
		return cartRepository.save(cart);
	}


	/*
	 * Return cart size
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public void countCartSize(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String token = (String) session.getAttribute("token");
		
		// Return clone cart size: ( if token is null )
		if(token == null) {
			List<Cart> cartsSession = (List<Cart>) session.getAttribute("cartsSession");
			int cartSize = 0;
			if(cartsSession != null) {
				cartSize = cartsSession.size();
			}
			session.setAttribute("cartSize", cartSize);
		}
		else {
		// Return cart size ( if token is valid )
			Long idUser = jwtTokenProvider.getUserIdFromJWT(token);
			User user = userRepository.getById(idUser);
			String username = user.getUsername();
			session.setAttribute("username", username);
			List<Cart> listProductInCarts = cartRepository.findByIdUser(idUser);
			if( listProductInCarts != null ) {
				int totalQuantity = listProductInCarts.size();
				session.setAttribute("cartSize", totalQuantity);						
			}
			else {
				session.setAttribute("cartSize", 0);
			}
		}
	}


	/*
	 * Update product quantity
	 * Created by: NPTAN (10/04/2022)
	 * Version: 1.0
	 */
	@Override
	public Cart update(Long id, Long quantityUpdate) {
		Cart cart = cartRepository.findById(id).get();
		Long quantityCurrent = cart.getQuantity();
		cart.setQuantity(quantityCurrent + quantityUpdate);
		cartRepository.save(cart);
		return cart;
	}
	
}
