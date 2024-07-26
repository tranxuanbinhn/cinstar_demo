package com.xb.cinstar.repository;

import com.xb.cinstar.models.TicketModel;
import com.xb.cinstar.models.TicketOrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITicketRespository extends JpaRepository<TicketModel, Long> {

}
