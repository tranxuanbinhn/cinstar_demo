package com.xb.cinstar.repository;

import com.xb.cinstar.models.FoodModel;
import com.xb.cinstar.models.TicketModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFoodRespository extends JpaRepository<FoodModel, Long> {

}
