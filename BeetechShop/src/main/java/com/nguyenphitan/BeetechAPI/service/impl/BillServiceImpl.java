package com.nguyenphitan.BeetechAPI.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nguyenphitan.BeetechAPI.entity.OrderAccount;
import com.nguyenphitan.BeetechAPI.entity.OrderDetail;
import com.nguyenphitan.BeetechAPI.entity.Product;
import com.nguyenphitan.BeetechAPI.entity.User;
import com.nguyenphitan.BeetechAPI.entity.discount.Discount;
import com.nguyenphitan.BeetechAPI.payload.BillRequest;
import com.nguyenphitan.BeetechAPI.payload.BillResponse;
import com.nguyenphitan.BeetechAPI.payload.ProductRequest;
import com.nguyenphitan.BeetechAPI.payload.ProductResponse;
import com.nguyenphitan.BeetechAPI.repository.OrderAccountRepository;
import com.nguyenphitan.BeetechAPI.repository.OrderDetailRepository;
import com.nguyenphitan.BeetechAPI.repository.ProductRepository;
import com.nguyenphitan.BeetechAPI.repository.UserRepository;
import com.nguyenphitan.BeetechAPI.service.BillService;
import com.nguyenphitan.BeetechAPI.service.admin.DiscountService;

/**
 * Bill service implements
 * @author ADMIN
 *
 */
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
	
	@Autowired
	private DiscountService discountService;
	
	/*
	 * Get alls
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public List<BillResponse> getAllBills() {
		// Get alls
		List<OrderAccount> orderAccounts = orderAccountRepository.findAll();
		
		// Get detail
		List<OrderDetail> orderDetails = orderDetailRepository.findAll();
		
		// Build dto (id, user, listProduct, totalPrice, purchaseDate, status)
		List<BillResponse> billResponses = new ArrayList<BillResponse>();
		for(OrderAccount orderAccount : orderAccounts) {
			Long billId = orderAccount.getId();
			User user = userRepository.getById(orderAccount.getUserId()); 	// get user
			Date orderDate = orderAccount.getOrderDate();	// purchaseDate
			Integer status = orderAccount.getStatus();	// status
			List<ProductResponse> products = new ArrayList<ProductResponse>();	// listProducts
			List<Discount> discounts = discountService.getAlls();	// discounts
			Double total = 0D;	// totalPrice
			for(OrderDetail orderDetail : orderDetails) {
				if( orderAccount.getId() == orderDetail.getOrderAccountId() ) {
					Product product = productRepository.getById(orderDetail.getProductId());
					products.add( new ProductResponse(product, orderDetail.getQuantity()) );
					Long totalPrice = orderDetail.getQuantity() * product.getPrice();
					total += totalPrice;
				}
			}
			
			for(int i = 0 ; i < discounts.size() ; i++) {
				Double nextValue = discounts.get(i).getValue();
				if( total < nextValue ) {
					if( i > 0 ) {
						total -= (total * discounts.get(i-1).getDiscount()/100);
						break;						
					}
					else {
						break;
					}
				}
			}
			
			billResponses.add( new BillResponse(billId, user, products, orderDate, status, total) );
			
		}
		return billResponses;
	}

	
	/*
	 * Create bill
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public OrderAccount create(BillRequest billRequest) {
		Long userId = billRequest.getUserId();
		OrderAccount orderAccount = new OrderAccount(userId);	// order account
		OrderAccount orderAccountCurrentSave = orderAccountRepository.save(orderAccount);
		Long orderAccountId = orderAccountCurrentSave.getId();
		
		// Build order detail
		List<ProductRequest> productRequests = billRequest.getProductRequests();
		List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
		for(ProductRequest productRequest : productRequests) {
			OrderDetail orderDetail = new OrderDetail(orderAccountId, productRequest.getIdProduct(), productRequest.getQuantitySelected());
			orderDetails.add(orderDetail);
		}
		orderDetailRepository.saveAll(orderDetails);
		
		return orderAccountCurrentSave;
	}

	
	/*
	 * Update bill with id
	 * Created by: NPTAN
	 * Version: 1.0
	 */
	@Override
	public OrderAccount update(Long id) {
		OrderAccount orderAccount = orderAccountRepository.getById(id);
		orderAccount.setStatus(1);
		return orderAccountRepository.save(orderAccount);
	}

}
