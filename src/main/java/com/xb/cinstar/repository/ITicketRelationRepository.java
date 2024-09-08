package com.xb.cinstar.repository;

import com.xb.cinstar.models.TicketRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITicketRelationRepository extends JpaRepository<TicketRelation, Long> {
    List<TicketRelation> findAllByTicketorderId(Long id);

}
