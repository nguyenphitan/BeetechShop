package com.nguyenphitan.BeetechAPI.controller;

import java.util.List;

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
import com.nguyenphitan.BeetechAPI.service.BillService;
import com.nguyenphitan.BeetechAPI.service.CartService;
import com.nguyenphitan.BeetechAPI.service.DiscountService;
import com.nguyenphitan.BeetechAPI.service.VNPayService;

/**
 * Controller quản lý các page
 * Created by: NPTAN
 * Version: 1.0
 */
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
	
	@Autowired
	DiscountService discountService;
	
	@Autowired
	VNPayService vnPayService;
	
	@Autowired
	BillService billService;
	
	/*
	 * Hiển thị trang home (danh sách các sản phẩm)
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/")
	public ModelAndView indexPage(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("products", productRepository.findAll());
		cartService.countCartSize(request);
		return modelAndView;
	}
	
	
	/*
	 * Hiển thị đăng nhập
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("auth/login")
	public ModelAndView loginPage() {
		ModelAndView modelAndView = new ModelAndView("login");
		return modelAndView;
	}
	
	
	/*
	 * Hiển thị đăng ký
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("auth/register")
	public ModelAndView registerPage() {
		ModelAndView modelAndView = new ModelAndView("register");
		return modelAndView;
	}
	
	
	/*
	 * Hiển thị trang quản lý sản phẩm
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/admin-product")
	public ModelAndView adminPage() {
		ModelAndView modelAndView = new ModelAndView("admin/product");
		List<Product> products = productRepository.findAll();
		modelAndView.addObject("products", products);
		return modelAndView;
	}
	
	
	/*
	 * Hiển thị trang thêm mới sản phẩm
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/admin/add-product")
	public ModelAndView addProductPage() {
		ModelAndView modelAndView = new ModelAndView("admin/add-product");
		return modelAndView;
	}
	
	
	/*
	 * Hiển thị trang thêm mới danh sách sản phẩm
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/admin/add-list")
	public ModelAndView addListProduct() {
		ModelAndView modelAndView = new ModelAndView("admin/add-list-product");
		return modelAndView;
	}
	
	
	/*
	 * Hiển thị trang chi tiết sản phẩm
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/detail")
	public ModelAndView detailPage(@RequestParam("id") Long id, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("detail");
		modelAndView.addObject("product", productRepository.findById(id));
		cartService.countCartSize(request);
		return modelAndView;
	}
	
	
	/*
	 * Hiển thị danh sách sản phẩm trong giỏ hàng
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/list-cart")
	public ModelAndView cartPage(HttpServletRequest request) {
		ModelAndView modelAndView = cartService.getAllCart("cart", request);
		return modelAndView;
	}
	
	
	/*
	 * Hiển thị trang thêm mới mã giảm giá
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/admin/add-discount")
	public ModelAndView createDiscount() {
		ModelAndView modelAndView = new ModelAndView("admin/add-discount");
		return modelAndView;
	}
	
	
	/*
	 * Hiển thị trang quản lý mã giảm giá
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/admin-discount")
	public ModelAndView discountPage(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("admin/discount");
		modelAndView.addObject("discounts", discountService.getAlls());
		return modelAndView;
	}
	
	
	/*
	 * Hiển thị trang quản lý hóa đơn
	 * Created by: NPTAN (06/05/2022)
	 * Version: 1.0
	 */
	@GetMapping("/admin-bill")
	public ModelAndView billManagerPage() {
		return billService.billManagerPage();
	}
	
	
	/*
	 * Hiển thị giao diện thanh toán online
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@GetMapping("/payment")
	public ModelAndView payment() {
		ModelAndView modelAndView = new ModelAndView("vnpay");
		return modelAndView;
	}
	
	
	/*
	 * Hiển thị thông tin sau khi thanh toán cho khách hàng
	 * Created by: NPTAN
	 * Version: 1.0
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
		return vnPayService.vnpayReturnPage(
				amount, bankCode, bankTranNo, cardType, orderInfo, payDate, responseCode, tmnCode, transactionNo, transactionStatus, txnRef, secureHash);
	}
}
