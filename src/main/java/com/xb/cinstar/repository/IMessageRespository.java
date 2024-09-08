package com.xb.cinstar.repository;

import com.xb.cinstar.models.MessageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMessageRespository extends JpaRepository<MessageModel,Long> {

    @Query(value = "select * from message_model where (receiver_id = ?1 and sender_id = ?2) or (receiver_id = ?2 and sender_id = ?1) order by create_date desc", nativeQuery = true)
    List<MessageModel> findAllByReceiverAndSender(Long sender, Long receiver);

    @Query(value = "select * from message_model where receiver_id = ?1 or sender_id = ?1 ", nativeQuery = true)
    List<MessageModel> findAllByUser(Long username);


}
