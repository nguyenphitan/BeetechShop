package com.nguyenphitan.BeetechAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nguyenphitan.BeetechAPI.entity.OrderAccount;

/**
 * Order account repository
 * @author ADMIN
 *
 */
@Repository
public interface OrderAccountRepository extends JpaRepository<OrderAccount, Long> {

}
