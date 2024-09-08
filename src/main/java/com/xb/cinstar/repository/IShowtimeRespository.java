package com.xb.cinstar.repository;

import com.xb.cinstar.models.ShowTimeModel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository()
public interface IShowtimeRespository extends JpaRepository<ShowTimeModel, Long> {
        @Query(value = "select * from showtime where movie_id = ?1 and theater_id = ?2 and date(date)=?3 and date_time>current_timestamp() order by date_time", nativeQuery = true
    )
    List<ShowTimeModel> showAll(Long movieId, Long theaterId, String date);
    @Query(value = "select count(*) from showtime where movie_id = ?1 and theater_id = ?2 and date(date)=?3", nativeQuery = true
    )
    Long countShowAll(Long movieId, Long theaterId, String date);

    @Query(value = "select * from showtime where movie_id = ?1 and date(date)=?2 and date_time>current_timestamp()", nativeQuery = true
    )
    List<ShowTimeModel> showAll(Long movieId,String date);
    @Query(value = "select count(*) from showtime where movie_id = ?1 and date(date)=?2 order by date_time", nativeQuery = true
    )
    Long countShowAll(Long movieId,String date);

    @Query(value = "select * from showtime where date(date)=?1 and date_time>current_timestamp()", nativeQuery = true
    )
    List<ShowTimeModel> showAll(String date);
    @Query(value = "select count(*) from showtime where date(date)=?1 order by date_time", nativeQuery = true
    )
    Long countShowAll(String date);

    @Query(value = "select * from showtime where movie_id = ?", nativeQuery = true)
    List<ShowTimeModel> findAllByMovieId(Long id);
    @Query(value = "select * from showtime where movie_id = ?1 and date_time > current_time() limit 3", nativeQuery = true)
    List<ShowTimeModel> findThreeByMovieId(Long id);
    @Query(value = " select distinct showtime.id,date(date) from showtime where date(date)>= current_date() limit 5", nativeQuery = true)
    List<ShowTimeModel> findShowtimeGreateThanCurrentDate();

    @Query(value = " select * from showtime where date between current_date() and current_date()+2", nativeQuery = true)
    List<ShowTimeModel> selectShowTimeFromCurrentDateToTwoDateLater();

    @Query(value = " select * from showtime where date_time > current_date();", nativeQuery = true)
    Page<ShowTimeModel> findAll(Pageable pageable);
    @Query(value = " select count(*) from showtime where date_time > current_date();", nativeQuery = true)
    long count();
}
