package com.nguyenphitan.BeetechAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nguyenphitan.BeetechAPI.entity.OrderDetail;

/**
 * Order detail repository
 * @author ADMIN
 *
 */
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

}
