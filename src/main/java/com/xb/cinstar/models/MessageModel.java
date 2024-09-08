package com.xb.cinstar.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class MessageModel extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private UserModel sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private UserModel receiver;

    private String message;
    private Date date;

}
