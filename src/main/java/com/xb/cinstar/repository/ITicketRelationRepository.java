package com.xb.cinstar.repository;

import com.xb.cinstar.models.TicketRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITicketRelationRepository extends JpaRepository<TicketRelation, Long> {
    List<TicketRelation> findAllByTicketorderId(Long id);

    @Query(value = "select count(*) from ticket_relation where showtime_id = ?1 and seat_id = ?2", nativeQuery = true)
    Long countTicketRelationByShowtimeAndSeat(Long showtimeid, Long seatid);

}
