package com.xb.cinstar.controllers.chat;

import com.xb.cinstar.dto.Message;
import com.xb.cinstar.service.impl.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Controller
public class ChatController {
    @Autowired private MessageService messageService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message){
        return message;
    }


    @MessageMapping("/admin/private-message")
    public Message recMessageUser(@Payload Message message){
        message.setDate(new Date());
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        System.out.println(message.toString());
        Message result = messageService.save(message);
        return message;
    }
    @MessageMapping("/user/private-message")
    public Message recMessageAdmin(@Payload Message message){
        message.setDate(new Date());
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        Message result = messageService.save(message);
        System.out.println(message.toString());
        return message;
    }
}
