package com.xb.cinstar.repository;

import com.xb.cinstar.models.MovieModel;
import com.xb.cinstar.models.TheaterModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITheaterRespository extends JpaRepository<TheaterModel, Long> {
    @Query(value = "select * from theater where name like %?1%", nativeQuery = true)
    public List<TheaterModel> findTheaterByKeyword(String keyword);
}
