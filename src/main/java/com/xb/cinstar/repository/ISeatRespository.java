package com.xb.cinstar.repository;

import com.xb.cinstar.models.SeatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISeatRespository extends JpaRepository<SeatModel, Long> {
}
