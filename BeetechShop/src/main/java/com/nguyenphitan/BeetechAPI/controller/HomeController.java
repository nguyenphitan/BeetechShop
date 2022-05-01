package com.nguyenphitan.BeetechAPI.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.nguyenphitan.BeetechAPI.entity.Product;
import com.nguyenphitan.BeetechAPI.jwt.JwtTokenProvider;
import com.nguyenphitan.BeetechAPI.repository.CartRepository;
import com.nguyenphitan.BeetechAPI.repository.ProductRepository;
import com.nguyenphitan.BeetechAPI.repository.UserRepository;
import com.nguyenphitan.BeetechAPI.repository.discount.DiscountRepository;
import com.nguyenphitan.BeetechAPI.service.CartService;

@Controller
public class HomeController {
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired 
	UserRepository userRepository;
	
	@Autowired
	DiscountRepository discountRepository;
	
	@Autowired
	CartService cartService;
	
	/*
	 * Home (hiển thị danh sách sản phẩm)
	 */
	@GetMapping("/")
	public ModelAndView indexPage(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("products", productRepository.findAll());
		cartService.countCartSize(request);
		return modelAndView;
	}
	
	/*
	 * Đăng nhập
	 */
	@GetMapping("auth/login")
	public ModelAndView loginPage() {
		ModelAndView modelAndView = new ModelAndView("login");
		return modelAndView;
	}
	
	/*
	 * Đăng ký tài khoản
	 */
	@GetMapping("auth/register")
	public ModelAndView registerPage() {
		ModelAndView modelAndView = new ModelAndView("register");
		return modelAndView;
	}
	
	/*
	 * Admin quản lý sản phẩm
	 */
	@GetMapping("/admin-product")
	public ModelAndView adminPage() {
		ModelAndView modelAndView = new ModelAndView("admin/product");
		List<Product> products = productRepository.findAll();
		modelAndView.addObject("products", products);
		return modelAndView;
	}
	
	/*
	 * Admin thêm một sản phẩm
	 */
	@GetMapping("/admin/add-product")
	public ModelAndView addProductPage() {
		ModelAndView modelAndView = new ModelAndView("admin/add-product");
		return modelAndView;
	}
	
	/*
	 * Admin thêm danh sách sản phẩm
	 */
	@GetMapping("/admin/add-list")
	public ModelAndView addListProduct() {
		ModelAndView modelAndView = new ModelAndView("admin/add-list-product");
		return modelAndView;
	}
	
	
	/*
	 * Chi tiết sản phẩm
	 */
	@GetMapping("/detail")
	public ModelAndView detailPage(@RequestParam("id") Long id, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("detail");
		modelAndView.addObject("product", productRepository.findById(id));
		cartService.countCartSize(request);
		return modelAndView;
	}
	
	/*
	 * Danh sách giỏ hàng
	 */
	@GetMapping("/list-cart")
	public ModelAndView cartPage(HttpServletRequest request) {
		ModelAndView modelAndView = cartService.getAllCart("cart", request);
		return modelAndView;
	}
	
	/*
	 * Giao diện hóa đơn
	 */
	@GetMapping("/admin-bill")
	public ModelAndView billPage(HttpServletRequest request) {
		ModelAndView modelAndView = cartService.getAllCart("admin/bill", request);
		return modelAndView;
	}	
	/*
	 * Giao diện thanh toán
	 */
	@GetMapping("/payment")
	public ModelAndView payment() {
		ModelAndView modelAndView = new ModelAndView("vnpay");
		return modelAndView;
	}
	
	/*
	 * Trả về thông tin thanh toán cho khách hàng
	 */
	@GetMapping("/vnpay_return")
	public ModelAndView returnPage(
	
			@RequestParam("vnp_Amount") String amount,
			@RequestParam("vnp_BankCode") String bankCode,
			@RequestParam("vnp_BankTranNo") String bankTranNo,
			@RequestParam("vnp_CardType") String cardType,
			@RequestParam("vnp_OrderInfo") String orderInfo,
			@RequestParam("vnp_PayDate") String payDate,
			@RequestParam("vnp_ResponseCode") String responseCode,
			@RequestParam("vnp_TmnCode") String tmnCode,
			@RequestParam("vnp_TransactionNo") String transactionNo,
			@RequestParam("vnp_TransactionStatus") String transactionStatus,
			@RequestParam("vnp_TxnRef") String txnRef,
			@RequestParam("vnp_SecureHash") String secureHash
			
	) {
		ModelAndView modelAndView = new ModelAndView("vnpay_return");
		
		Map<String, String> data = new HashMap<String, String>();
		data.put("amount", amount);
		data.put("bankCode", bankCode);
		data.put("bankTranNo", bankTranNo);
		data.put("cardType", cardType);
		data.put("orderInfo", orderInfo);
		data.put("payDate", payDate);
		data.put("responseCode", responseCode);
		data.put("tmnCode", tmnCode);
		data.put("transactionNo", transactionNo);
		data.put("transactionStatus", transactionStatus);
		data.put("txnRef", txnRef);
		data.put("secureHash", secureHash);
		
		modelAndView.addObject("data", data);
		
		return modelAndView;
	}
}
