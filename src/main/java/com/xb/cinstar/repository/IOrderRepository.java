package com.xb.cinstar.repository;

import com.xb.cinstar.models.OrderModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<OrderModel,Long> {
    @Query(value = "select * from  orders where user_id = ?1 and status = true", nativeQuery = true)
    Page<OrderModel> findAllByUserId(Long id, Pageable pageable);
    @Query(value = "select * from  orders where user_id = ?1 and status = true", nativeQuery = true)
    List<OrderModel> findAllByUserId(Long id);

    @Query(value = "select * from orders where status = true", nativeQuery = true)
    Page<OrderModel> findAll(Pageable pageable);
    @Query(value = "select count(*) from orders where status = true", nativeQuery = true)
    long count();
}