package com.xb.cinstar.repository;

import com.xb.cinstar.models.SeatModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISeatRespository extends JpaRepository<SeatModel, Long> {
    @Query(value = "select s.* from seat s inner join screen_seat sc on s.id = sc.seat_id where screen_id = ?", nativeQuery = true)
    List<SeatModel> findByScreenId(Long screenId);
}
