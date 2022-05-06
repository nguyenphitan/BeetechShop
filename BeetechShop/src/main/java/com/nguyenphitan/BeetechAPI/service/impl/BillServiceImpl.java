package com.nguyenphitan.BeetechAPI.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.nguyenphitan.BeetechAPI.entity.OrderAccount;
import com.nguyenphitan.BeetechAPI.entity.OrderDetail;
import com.nguyenphitan.BeetechAPI.entity.Product;
import com.nguyenphitan.BeetechAPI.entity.User;
import com.nguyenphitan.BeetechAPI.payload.BillResponse;
import com.nguyenphitan.BeetechAPI.payload.ProductResponse;
import com.nguyenphitan.BeetechAPI.repository.OrderAccountRepository;
import com.nguyenphitan.BeetechAPI.repository.OrderDetailRepository;
import com.nguyenphitan.BeetechAPI.repository.ProductRepository;
import com.nguyenphitan.BeetechAPI.repository.UserRepository;
import com.nguyenphitan.BeetechAPI.service.BillService;

@Service
public class BillServiceImpl implements BillService {
	
	@Autowired
	private OrderAccountRepository orderAccountRepository;

	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public ModelAndView billManagerPage() {
		ModelAndView modelAndView = new ModelAndView("admin/bill");
		
		// 1. Lấy ra danh sách các hóa đơn:
		List<OrderAccount> orderAccounts = orderAccountRepository.findAll();
		
		// 2. Lấy ra danh sách chi tiết của các hóa đơn:
		List<OrderDetail> orderDetails = orderDetailRepository.findAll();
		
		// 3. Danh sách dữ liệu hóa đơn trả về: (id hóa đơn, thông tin khách hàng, danh sách sản phẩm kèm số lượng, tổng tiền, ngày mua, trạng thái hóa đơn)
		List<BillResponse> billResponses = new ArrayList<BillResponse>();
		for(OrderAccount orderAccount : orderAccounts) {
			// Lấy ra id hóa đơn:
			Long billId = orderAccount.getId();
			// Lấy ra thông tin nhân viên:
			User user = userRepository.getById(orderAccount.getUserId());
			// Lấy ra mua: (ngày tạo hóa đơn)
			Date orderDate = orderAccount.getOrderDate();
			// Lấy ra trạng thái hóa đơn:
			Integer status = orderAccount.getStatus();
			// List danh sách sản phẩm trong hóa đơn:
			List<ProductResponse> products = new ArrayList<ProductResponse>();
			// Tổng tiền hóa đơn:
			Long total = 0L;
			for(OrderDetail orderDetail : orderDetails) {
				if( orderAccount.getId() == orderDetail.getOrderAccountId() ) {
					// Thêm sản phẩm vào list hóa đơn:
					Product product = productRepository.getById(orderDetail.getProductId());
					products.add( new ProductResponse(product, orderDetail.getQuantity()) );
					Long totalPrice = orderDetail.getQuantity() * product.getPrice();
					total += totalPrice;
				}
			}
			
			billResponses.add( new BillResponse(billId, user, products, orderDate, status, total) );
			
		}
		
		modelAndView.addObject("billResponses", billResponses);
		return modelAndView;
	}

}
