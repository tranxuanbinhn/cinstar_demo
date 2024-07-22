package com.xb.cinstar.repository;

import com.xb.cinstar.models.TheaterModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITheaterRespository extends JpaRepository<TheaterModel, Long> {
}
