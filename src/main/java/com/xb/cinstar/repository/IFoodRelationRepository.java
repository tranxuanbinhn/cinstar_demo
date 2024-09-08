package com.xb.cinstar.repository;

import com.xb.cinstar.models.FoodRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFoodRelationRepository extends JpaRepository<FoodRelation, Long> {
    List<FoodRelation> findAllByTicketorderId(Long id);
}
