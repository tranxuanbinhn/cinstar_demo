package com.xb.cinstar.repository;

import com.xb.cinstar.models.MovieModel;
import com.xb.cinstar.models.TheaterModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITheaterRespository extends JpaRepository<TheaterModel, Long>  {
    @Query(value = "select * from theater where name like %?1%", nativeQuery = true)
     List<TheaterModel> findTheaterByKeyword(String keyword);

    List<TheaterModel> findAllByCity(String city);

    @Query(value = "select t.* from theater t inner join showtime s on t.id = s.theater_id inner join ticket_relation tr on s.id = tr.showtime_id inner join ticket_order tk on tr.ticket_order = tk.id inner join orders o on tk.id = o.ticketorder_id where o.id = ?", nativeQuery = true)
    TheaterModel findTheaterByOrderId(Long id);
}
