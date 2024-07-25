package com.xb.cinstar.repository;

import com.xb.cinstar.models.ShowTimeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IShowtimeRespository extends JpaRepository<ShowTimeModel, Long> {
    @Query(value = "select * from showtime where movie_id = ?1 and theater_id = ?2 and date(date)=?3", nativeQuery = true
    )
    List<ShowTimeModel> showAll(Long movieId, Long theaterId, LocalDateTime date);

    @Query(value = "select * from showtime where movie_id = ?1 and date(date)=?2", nativeQuery = true
    )
    List<ShowTimeModel> showAll(Long movieId,LocalDateTime date);

}
