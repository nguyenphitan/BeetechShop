package com.nguyenphitan.BeetechAPI.repository.discount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nguyenphitan.BeetechAPI.entity.discount.Discount;

/**
 * Discount repository
 * @author ADMIN
 *
 */
@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

}
