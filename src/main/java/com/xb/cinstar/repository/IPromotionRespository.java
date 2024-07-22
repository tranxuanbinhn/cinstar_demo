package com.xb.cinstar.repository;

import com.xb.cinstar.models.PromotionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPromotionRespository extends JpaRepository<PromotionModel, Long> {
}
