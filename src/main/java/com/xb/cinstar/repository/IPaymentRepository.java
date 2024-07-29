package com.xb.cinstar.repository;

import com.xb.cinstar.models.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaymentRepository extends JpaRepository<PaymentModel, Long> {
}
