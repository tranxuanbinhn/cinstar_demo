package com.xb.cinstar.repository;

import com.xb.cinstar.models.TicketOrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ITicketOrderRespository extends JpaRepository<TicketOrderModel,Long> {

    @Query(value = "select * from ticket_order where order_id = ?", nativeQuery = true)
    TicketOrderModel findByOrderId(Long orderId);

}
