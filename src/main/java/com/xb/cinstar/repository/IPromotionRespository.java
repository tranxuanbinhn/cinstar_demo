package com.xb.cinstar.repository;

import com.xb.cinstar.models.PromotionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPromotionRespository extends JpaRepository<PromotionModel, Long> {

    @Query(value = "select p.* from promotion p inner join user_promotion u  on p.id = promotion_id where u.user_id = ?", nativeQuery = true)
    List<PromotionModel> findAllByUserId(Long id);
}
